package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.backend.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Base CLI tool for adding locations. Implements common flags that all Locations share and requires subclasses to
 * implement methods needed to create their corresponding Location objects.
 */
abstract class AddLocation extends BaseCLI {
    // Maps cli commands to the correct AddLocation subclass to use
    private static final HashMap<String, Class<? extends AddLocation>> locationMap = new HashMap<String, Class<? extends AddLocation>>() {{
        put("hotel", AddHotel.class);
        put("shop", AddShop.class);
        put("bus-stop", AddBusStop.class);
    }};

    @Parameter(names = {"--name"}, description = "The name of the location.", required = true)
    private String name;

    @Parameter(names = {"--x"}, description = "The x-coordinate of the location.", required = true)
    private double X;

    @Parameter(names = {"--y"}, description = "The y-coordinate of the location.", required = true)
    private double Y;

    /**
     * Get the appropriate AddLocation CLI object from a string representation.
     *
     * @param parserString String representation of the AddLocation object.
     * @return AddLocation object corresponding to parserString.
     * @throws IllegalArgumentException if no AddLocation object exists for the given string.
     */
    static AddLocation getMatchingParser(String parserString) throws IllegalArgumentException {
        try {
            return locationMap.get(parserString).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            // Only the null pointer should ever be caught here if locationMap has been configured with correct objects
            throw new IllegalArgumentException("The location type specified is not recognised.");
        }
    }

    String getName() {
        return name;
    }

    double getX() {
        return X;
    }

    double getY() {
        return Y;
    }

    /**
     * Convert this objects data to a Location object.
     *
     * @return new Location.
     */
    abstract Location toLocation();
}
