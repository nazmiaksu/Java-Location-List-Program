package locations.backend;

import java.util.Comparator;

/**
 * A class to compare two Location objects based on their distance to a specified point.
 */
public class LocationComparator implements Comparator<Location> {
    // Reference coordinates to compare to
    private final double X;
    private final double Y;

    /**
     * Create a new Comparator that compares the distance of two Locations based on coordinates X and Y.
     *
     * @param X The x-coordinate to measure distance to.
     * @param Y The y-coordinate to measure distance to.
     */
    public LocationComparator(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Compare the distance of two Locations relative to (this.X, this.Y) as a reference point.
     *
     * @param o1 The first Location to compare.
     * @param o2 The second Location to compare.
     * @return int: 1 if o1 is closer, 0 if they are equidistant or -1 if o2 is closer.
     */
    @Override
    public int compare(Location o1, Location o2) {
        // code adapted from Merino, 2014
        return Double.compare(o1.distance(X, Y), o2.distance(X, Y));
        // end of adapted code
    }
}