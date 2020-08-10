package locations.backend;

import java.util.List;

public class BusStop extends Location {
    private List<String> buses; // list of buses that stop here

    public BusStop(String name, double X, double Y, List<String> buses) {
        super(name, X, Y);
        this.buses = buses;
    }

    /**
     * Get a human readable representation of a BusStop.
     *
     * @return BusStop as a string.
     */
    @Override
    public String toString() {
        return "Bus stop {" +
                "name='" + this.getName() + '\'' +
                ", X=" + this.getX() +
                ", Y=" + this.getY() +
                ", buses=" + this.buses.toString() +
                '}';
    }
}
