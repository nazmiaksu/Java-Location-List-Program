package locations.CLI;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import locations.backend.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;

class Main {
    private static Collection<Location> locations; // Collection of all saved locations

    /**
     * Entry point for the Location List program. This method constructs the CLI tool used to get and set locations and
     * offers an easy way to call the methods of Location and its subclasses.
     */
    public static void main(String[] args) {
        // Storage location in user home
        File storageFile = Paths.get(System.getProperty("user.home"), "locationList.serialised").toFile();
        // Print start text
        String startTextPath = "/StartText.txt";
        printStartScreen(startTextPath);
        // Instantiate and load the locations list
        loadLocations(storageFile);
        // Parse CLI commands until user quits
        readCommands();
        // Save locations when readCommands returns
        saveLocations(storageFile);
    }

    /**
     * Load and print the welcome text file.
     *
     * @param startTextPath Path containing the text to print
     */
    private static void printStartScreen(String startTextPath) {
        // code adapted from Kim, 2019
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(startTextPath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            // end of adapted code
        } catch (IOException | NullPointerException ignored) {
            // Continue as normal if not found
        }
    }

    /**
     * Loads all previously stored locations to a static variable.
     */
    @SuppressWarnings("unchecked") // If the program is used as intended the stored data should have the correct type
    private static void loadLocations(File storageFile) {
        if (storageFile.exists()) {
            // Load existing file
            try {
                locations = (ArrayList<Location>) Location.readCollectionFromFile(storageFile.getPath());
                System.out.println(String.format("Loaded location data from: %s", storageFile.getPath()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Could not read the location storage file. Please verify that it is not corrupted.");
                System.exit(1);
            }
        } else {
            // Create new list of locations
            locations = new ArrayList<>();
            System.out.println(String.format("Creating new location data at: %s", storageFile.getPath()));
        }
    }

    /**
     * Save all locations to a file. Overwrites file contents if present.
     */
    private static void saveLocations(File storageFile) {
        try {
            Location.writeCollectionToFile(locations, storageFile.getPath());
            System.out.println(String.format("Successfully saved location data to: %s", storageFile.getPath()));
        } catch (IOException e) {
            System.err.println("Could not save location data. Data may have been lost.");
        }

    }

    /**
     * Read CLI commands until the user chooses to quit. On exit, the list of locations will be saved.
     */
    private static void readCommands() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Commands from user as a string and split to an array
        String line;
        String[] argv;
        // Represents available commands a user can issue
        Action action;
        JCommander argParser;
        // Represents available distance options
        Distance displayDistance;
        JCommander distanceParser;
        // Represents available display options
        Display displayLocations;
        JCommander displayParser;
        // Represents available add options and chosen location type
        AddLocation addLocation;
        String locationType;

        while (true) { // Read input until user quits
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("An unknown parsing exception occurred.");
                continue;
            }
            // Build top level CLI commands
            action = new Action();
            argParser = JCommander.newBuilder().addObject(action).build();
            argParser.setProgramName("");
            // Parse commands
            argv = line.split("\\s+"); // Split arguments by whitespace
            try { // Wrap block as catching any Parameter exceptions will take the same action
                argParser.parse(argv[0]); // Parse only the first argument as the command to run

                if (action.isHelp()) { // Print help page
                    argParser.usage();

                } else if (action.isQuit()) { // Quit the program
                    return;

                } else if (action.isDistance()) { // Display the distance to a location
                    // Build distance commands
                    displayDistance = new Distance();
                    distanceParser = JCommander.newBuilder().addObject(displayDistance).build();
                    distanceParser.setProgramName("distance");
                    try {
                        distanceParser.parse(Arrays.copyOfRange(argv, 1, argv.length));
                        if (displayDistance.isHelp()) {
                            distanceParser.usage();
                        } else {
                            double distanceToLocation = displayDistance.getDistance(locations);
                            System.out.println(String.format("You are %s away from this location.", distanceToLocation));

                        }
                    } catch (NoSuchElementException e) {
                        System.err.println("No location with that name was found.");
                    }

                } else if (action.isDisplay()) { // Display a filtered list of locations
                    // Build display commands
                    displayLocations = new Display();
                    displayParser = JCommander.newBuilder().addObject(displayLocations).build();
                    displayParser.setProgramName("display");
                    displayParser.parse(Arrays.copyOfRange(argv, 1, argv.length));
                    if (displayLocations.isHelp()) {
                        displayParser.usage();
                    } else {
                        List<Location> filteredLocations = displayLocations.getFilteredLocations(locations);
                        if (filteredLocations.size() > 0) {
                            for (Location location : filteredLocations) {
                                System.out.println(location);
                            }
                        } else {
                            System.err.println("There are currently no locations of that type available. Please type " +
                                    "'help' to learn how to add locations.");
                        }

                    }

                } else if (action.isAdd()) { // Adds a new location using further arguments
                    try {
                        locationType = argv[1];
                        // Build add commands, AddLocation subclass used matches location type specified
                        addLocation = AddLocation.getMatchingParser(locationType);
                    } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                        argParser.usage();
                        System.err.println("No valid location type was specified.");
                        continue;
                    }
                    JCommander locationParser = JCommander.newBuilder().addObject(addLocation).build();
                    locationParser.setProgramName("add");
                    // Forward remaining args to locationParser
                    locationParser.parse(Arrays.copyOfRange(argv, 1, argv.length));
                    if (addLocation.isHelp()) {
                        locationParser.usage();
                    } else {
                        Location newLocation = addLocation.toLocation();
                        locations.add(newLocation);
                        System.out.println("Successfully added new location: " + newLocation.toString());
                    }

                } else { // Any other commands that are not recognised
                    System.err.println("'" + argv[0] + "' is not a recognised command.");
                    argParser.usage();
                }
            } catch (ParameterException e) { // Any missing parameters are caught here and print help
                System.err.println(e.getMessage());
            }
        }
    }
}