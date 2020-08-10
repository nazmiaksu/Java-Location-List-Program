package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.backend.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Display extends BaseCLI {
    // Map of CLI parameter to location class
    private static final HashMap<String, Class<? extends Location>> locationMap =
            new HashMap<String, Class<? extends Location>>() {{
                put("shop", Shop.class);
                put("hotel", Hotel.class);
                put("bus-stop", BusStop.class);
            }};

    @Parameter(names = {"--type"}, description = "The type of location to search for.", required = true)
    private String type;

    @SuppressWarnings("CanBeFinal")
    @Parameter(names = {"--nearest-first"}, description = "Order by by nearest locations first. Requires x and y" +
            "coordinates to also be specified or will default to nearest to (0, 0).")
    private boolean nearestFirst = false;

    @Parameter(names = {"--x"}, description = "Your x-coordinate.")
    private double X;

    @Parameter(names = {"--y"}, description = "Your y-coordinate.")
    private double Y;


    /**
     * Filter the list of locations passed using the specified parameters.
     *
     * @param locations List of Locations to search.
     * @return List of locations matching the specified filters.
     */
    List<Location> getFilteredLocations(Collection<Location> locations) {
        Stream<Location> filteredLocations = locations
                .stream()
                .filter(l -> l.getClass().equals(locationMap.get(this.type))); // location Class == type specified
        if (this.nearestFirst) {
            // Sort list by Locations closest to x, y specified by user
            filteredLocations = filteredLocations.sorted(new LocationComparator(this.X, this.Y));
        }
        return filteredLocations.collect(Collectors.toList());
    }
}
