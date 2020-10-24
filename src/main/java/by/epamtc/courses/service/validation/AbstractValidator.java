package by.epamtc.courses.service.validation;

import by.epamtc.courses.service.i18n.ResourceManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class for checking values of some entity attributes
 *
 * @author DEA
 */
public abstract class AbstractValidator {

    /**
     * Request's parameters with values from client
     */
    protected Map<String, String[]> parameterMap;

    /**
     * Map containing validation errors of parameters
     * Key - name of parameter, value - validation error message
     */
    protected Map<String, String> errors;

    /**
     * Instance of <code>ResourceManager</code> which get localized messages
     */
    protected ResourceManager resourceManager;

    /**
     * Construct a AbstractValidator
     *
     * @param parameterMap request's parameters with values from client
     * @param locale       locale of client to select translation of error messages
     */
    protected AbstractValidator(Map<String, String[]> parameterMap, Locale locale) {
        this.parameterMap = parameterMap;
        this.errors = new HashMap<>();
        this.resourceManager = new ResourceManager(locale);
    }

    /**
     * Check if string is empty
     *
     * @param line string for checking
     * @return true if string is empty or null
     */
    protected boolean checkEmpty(String line) {
        return line == null || line.isEmpty();
    }

    /**
     * Check if string array is empty
     *
     * @param parameters string array for checking
     * @return true if string array is empty or null
     */
    protected boolean checkEmpty(String[] parameters) {
        return parameters == null || parameters.length == 0;
    }

    /**
     * @return All errors which were found after validations
     */
    public Map<String, String> getErrors() {
        return errors;
    }
}
