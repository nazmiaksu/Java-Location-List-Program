package locations.backend;

import java.util.List;

public class Shop extends Location {
    private String type;
    private List<String> openingTimes; // List of opening times, one per day

    public Shop(String name, double X, double Y, String type, List<String> openingTimes) {
        super(name, X, Y);
        this.type = type;
        this.openingTimes = openingTimes;
    }

    /**
     * Get a human readable representation of a Shop.
     *
     * @return Shop as a string.
     */
    @Override
    public String toString() {
        return "Shop {" +
                "name='" + this.getName() + '\'' +
                ", X=" + this.getX() +
                ", Y=" + this.getY() +
                ", type=" + this.type +
                ", opening times=" + this.openingTimes.toString() +
                '}';
    }
}
