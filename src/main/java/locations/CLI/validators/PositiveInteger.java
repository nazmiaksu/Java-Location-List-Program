package locations.CLI.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PositiveInteger implements IParameterValidator {

    /**
     * Ensures a parameter is greater than 0.
     *
     * @param name  Name of parameter.
     * @param value User input value of the parameter.
     * @throws ParameterException If the parameters given are not valid.
     */
    @Override
    public void validate(String name, String value) throws ParameterException {
        // code adapted from Beust, 2017
        int n = Integer.parseInt(value);
        if (n < 1) {
            throw new ParameterException("Parameter " + name + " should be greater than 0 (found " + value + ")");
        }
        // end of adapted code
    }

}
