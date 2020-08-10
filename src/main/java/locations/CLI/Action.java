package locations.CLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the possible actions a user can take when using the Locations CLI tool. Each action is mutually exclusive
 * and calls another CLI tool to parse the remaining arguments as options for that action (e.g. calling "add" will
 * parse the remaining arguments as data defining a new Location using the AddLocation class).
 */
@SuppressWarnings("unused")
class Action {

    @SuppressWarnings("unused")
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"help", "--help"}, description = "Display this help page. Typing any other command followed " +
            "by 'help' will display help specific to that command.", help = true)
    private boolean help = false;

    @Parameter(names = {"add"}, description = "Add a new location. Type of location must follow this flag.")
    private boolean add = false;

    @Parameter(names = {"display"}, description = "Display a location or a list of locations matching a search.")
    private boolean display = false;

    @Parameter(names = {"distance"}, description = "Display the distance to a location from your x, y coordinates.")
    private boolean distance = false;

    @Parameter(names = {"quit", "exit", "close"}, description = "Quit the program and save all locations to a file.")
    private boolean quit = false;

    boolean isHelp() {
        return help;
    }

    boolean isAdd() {
        return add;
    }

    boolean isDisplay() {
        return display;
    }

    boolean isQuit() {
        return quit;
    }

    boolean isDistance() {
        return distance;
    }
}
