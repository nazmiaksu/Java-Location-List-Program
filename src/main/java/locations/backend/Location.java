package locations.backend;

import java.io.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a location with x and y coordinates and distance methods.
 */
public class Location implements Serializable {
    private String name; // Name of the location
    private double X; // X and Y coordinates
    private double Y;

    /**
     * Creates a Location with the specified name and coordinates.
     *
     * @param name The name of the Location.
     * @param X    The x-coordinate of the Location.
     * @param Y    The y-coordinate of the Location.
     */
    public Location(String name, double X, double Y) {
        this.name = name;
        this.X = X;
        this.Y = Y;
    }

    /**
     * Returns the straight line distance between a pair of x and y coordinates.
     *
     * @param x1 X coordinate of point 1.
     * @param y1 Y coordinate of point 1.
     * @param x2 X coordinate of point 2.
     * @param y2 Y coordinate of point 2.
     * @return Straight line distance between point 1 and point 2.
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        // distance = root(dx^2 + dy^2)
        double distanceX = x1 - x2;
        double distanceY = y1 - y2;
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    /**
     * Serialise a collection of objects to a new File at filePath. This will overwrite anything else at the path.
     *
     * @param objects  Collection of objects to serialise.
     * @param filePath File path to create the File containing the Collection.
     * @throws IOException If a File cannot be created or written to at filePath.
     */
    public static void writeCollectionToFile(Collection<? extends Serializable> objects, String filePath) throws IOException {
        // code adapted from Narang and Juneja, 2017
        try (ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Using try without catch so the file is auto closed
            objectStream.writeObject(objects);
            // end of adapted code
        }
    }

    /**
     * Read a collection of objects from a File and cast them to a Collection before returning.
     *
     * @param filePath Path to the File to read the Collection from.
     * @return Collection of objects contained in the file.
     * @throws IOException            If there is an issue reading the file or it does not contain serialised data.
     * @throws ClassNotFoundException If the Class in the serialised file is not defined.
     */
    public static Collection<?> readCollectionFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Collection<?>) objectStream.readObject();
        }
    }

    //<editor-fold desc="getters/setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return X;
    }
    //</editor-fold>

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    /**
     * Returns the straight line distance between a Location and a pair of x, y coordinates.
     *
     * @param x1 The x coordinate to measure to.
     * @param y1 The y coordinate to measure to.
     * @return Straight line distance between this location and the specified coordinates.
     */
    public double distance(double x1, double y1) {
        return Location.distance(this.getX(), this.getY(), x1, y1);
    }

    /**
     * Returns the straight line distance between a location and another location.
     *
     * @param location The other location to measure to.
     * @return Straight line distance between this location and another location.
     */
    public double distance(Location location) {
        return this.distance(location.getX(), location.getY());
    }

    /**
     * Get a human readable representation of a Location.
     *
     * @return Location as a string.
     */
    @Override
    public String toString() {
        return "Location {" +
                "name='" + name + "'" +
                ", X=" + X +
                ", Y=" + Y +
                "}";
    }

    /**
     * Check if 2 Locations are equal.
     *
     * @param o Another Location object.
     * @return Boolean true/false if equal or not equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.getX(), getX()) == 0 &&
                Double.compare(location.getY(), getY()) == 0 &&
                getName().equals(location.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getX(), getY());
    }
}
