package locations.backend;

public class Hotel extends Location {
    private int capacity; // number of rooms

    private int starRating; // 1-5 stars

    public Hotel(String name, double X, double Y, int capacity, int starRating) {
        super(name, X, Y);
        this.capacity = capacity;
        this.starRating = starRating;
    }

    /**
     * Get a human readable representation of a Hotel.
     *
     * @return Hotel as a string.
     */
    @Override
    public String toString() {
        return "Hotel {" +
                "name='" + this.getName() + '\'' +
                ", X=" + this.getX() +
                ", Y=" + this.getY() +
                ", capacity=" + this.capacity +
                ", star rating=" + this.starRating +
                '}';
    }
}
