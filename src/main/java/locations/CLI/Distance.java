package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.backend.Location;

import java.util.Collection;
import java.util.NoSuchElementException;

class Distance extends BaseCLI {

    @Parameter(names = {"--x"}, description = "Your x-coordinate.", required = true)
    private double X;

    @Parameter(names = {"--y"}, description = "Your y-coordinate.", required = true)
    private double Y;

    @Parameter(names = {"--name"}, description = "The name of the location.", required = true)
    private String name;

    /**
     * Get the distance to the location matching this.name using a list of Locations.
     *
     * @param locations Collection of Locations to search.
     * @return distance from this.x,this.y to Location matching this.name
     * @throws NoSuchElementException No location is present that matches the parameters.
     */
    Double getDistance(Collection<Location> locations) throws NoSuchElementException {
        Location chosenLocation = locations
                .stream()
                .filter(l -> l.getName().equals(this.name))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        return chosenLocation.distance(this.X, this.Y);
    }
}