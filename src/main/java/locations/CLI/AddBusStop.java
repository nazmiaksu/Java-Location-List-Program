package locations.CLI;

import com.beust.jcommander.Parameter;
import locations.backend.BusStop;
import locations.backend.Location;

import java.util.List;

class AddBusStop extends AddLocation {

    @Parameter(names = {"--buses"}, description = "The buses that stop at this bus stop (comma separated).",
            required = true)
    private List<String> buses;

    @Override
    public Location toLocation() {
        return new BusStop(this.getName(), this.getX(), this.getY(), this.buses);
    }
}
