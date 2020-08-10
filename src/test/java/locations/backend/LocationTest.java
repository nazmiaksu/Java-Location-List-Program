package locations.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


class LocationTest{
    private List<Location> locations; // List of locations to test with
    private String tempFile; // path to a temporary file to write to

    @BeforeEach
    public void setUp(){
        locations = new ArrayList<>();
        // Add 5 Locations all with different names and coordinates
        for (int i = 1; i <= 5; ++i) {
            locations.add(new Location(("location" + i), i, 2 * i));
        }
        //code adapted from Bafna, 2015
        String tempPathString = System.getProperty("java.io.tmpdir");
        Path tempPath = Paths.get(tempPathString, "locationList.serialised");
        // end of adapted code
        tempFile = tempPath.toString();
    }

    @AfterEach
    public void tearDown() throws Exception{
        // Ensure any data written is deleted after each test
        File testFile = new File(tempFile);
        try {
            Files.deleteIfExists(testFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getNames() {
        String name = locations.get(0).getName();
        Assertions.assertEquals("location1", name);
    }

    @Test
    void setName() {
        String newName = "newLocation";
        Location location = locations.get(0);
        Assertions.assertNotEquals(newName, location.getName());
        location.setName(newName);
        Assertions.assertEquals(newName, location.getName());
    }

    @Test
    void getX() {
        double X = locations.get(0).getX();
        Assertions.assertEquals(1, X);
    }

    @Test
    void setX() {
        double newX = 50.57;
        Location location = locations.get(0);
        Assertions.assertNotEquals(newX, location.getX()); // Check the value is actually being changed
        location.setX(newX);
        Assertions.assertEquals(newX, location.getX());
    }

    @Test
    void getY() {
        double Y = locations.get(0).getY();
        Assertions.assertEquals(2, Y);
    }

    @Test
    void setY() {
        double newY = 120.5;
        Location location = locations.get(0);
        Assertions.assertNotEquals(newY, location.getY());
        location.setY(newY);
        Assertions.assertEquals(newY, location.getY());
    }

    @Test
    void staticDistance() {
        // distance[(0, 0) -> (9, 0)] = 9
        Assertions.assertEquals(9.0, Location.distance(0, 0, 9, 0));
    }

    @Test
    void objectToCoordinatesDistance() {
        Location location = locations.get(0);
        // distance[(1, 2) -> (0, 0)] = root(5)
        Assertions.assertEquals(Math.sqrt(5), location.distance(0, 0));

    }

    @Test
    void objectToObjectDistance() {
        Location location = locations.get(0);
        // distance[(1, 2) -> (0, 0)] = root(5)
        Location otherLocation = new Location("testLocation", 0, 0);
        Assertions.assertEquals(Math.sqrt(5), location.distance(otherLocation));
    }

    @Test
    void testToString() {
        Location location = locations.get(0);
        // location1, 1, 2
        String expectedString = "Location {name='location1', X=1.0, Y=2.0}";
        Assertions.assertEquals(expectedString, location.toString());
    }

    @Test
    void writeCollectionToFile() {
        // code adapted from Boorlagadda, 2019
        Assertions.assertThrows(IOException.class, () -> Location.writeCollectionToFile(locations, "//not/a/valid" +
                "/path"));
        // end of adapted code
        // valid path should not throw exception and should write the file
        Assertions.assertDoesNotThrow(() -> Location.writeCollectionToFile(locations, tempFile));
    }

    @Test
    @SuppressWarnings("unchecked")
        // If the program is used as intended the stored data should have the correct type
    void readCollectionFromFile() {
        try {
            Location.writeCollectionToFile(locations, tempFile);
        } catch (IOException e) {

            e.printStackTrace();  // Should already be covered in another test anyway
        }
        List<Location> deserialisedLocations = null;
        try {
            deserialisedLocations = (ArrayList<Location>) Location.readCollectionFromFile(tempFile);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(locations, deserialisedLocations);
    }

    @Test
    void testEquals() {
        Location location = locations.get(0);
        Location identicalLocation = new Location("location1", 1, 2);
        Assertions.assertEquals(location, identicalLocation);
    }
}