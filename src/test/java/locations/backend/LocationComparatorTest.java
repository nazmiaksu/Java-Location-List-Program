package locations.backend;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LocationComparatorTest {

    private List<Location> locations; // List of locations to test with

    @BeforeEach
    public void setUp() throws Exception{
        locations = new ArrayList<>();
        // Add 5 Locations all with different names and coordinates
        for (int i = 1; i <= 5; ++i) {
            locations.add(new Location(("location" + i), i, 2 * i));
        }
    }

    @Test
    void compare_ShouldSortLocationsByDistance() {
        // Locations are initially sorted closest first in setUp
        List<Location> sortedLocations = new ArrayList<>(locations);
        // Randomise the order so they are not closest first
        Collections.shuffle(sortedLocations);
        sortedLocations.sort(new LocationComparator(0, 0)); // Sort relative to origin
        Assertions.assertEquals(locations, sortedLocations);
    }
}