package locations.CLI;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base CLI Class for all other classes to extend. Ensures that all other CLI tools can display a help page and
 * handle unknown parameters without failure.
 */
abstract class BaseCLI {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"help", "--help"}, description = "Display this help page.", help = true)
    private boolean help = false;

    public boolean isHelp() {
        return help;
    }
}
