package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.backend.Location;
import locations.backend.Shop;

import java.util.List;


class AddShop extends AddLocation {

    @Parameter(names = {"--type"}, description = "The type of the shop.", required = true)
    private String type;

    @Parameter(names = {"--hours"}, description = "The opening hours for each day of the week (comma separated).",
            required = true)
    private List<String> hours;

    @Override
    public Location toLocation() {
        return new Shop(this.getName(), this.getX(), this.getY(), this.type, this.hours);
    }
}
