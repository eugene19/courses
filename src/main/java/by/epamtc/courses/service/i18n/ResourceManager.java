package by.epamtc.courses.service.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for getting localized messages from property
 *
 * @author DEA
 */
public class ResourceManager {

    /**
     * Property file name which contains translations
     */
    private static final String RES_FILE_NAME = "strings";

    /**
     * Object of <code>ResourceBundle</code> which will take messages
     */
    private ResourceBundle resourceBundle;

    /**
     * Construct <code>ResourceManager</code> and initialize resourceBundle
     * depends on locale
     *
     * @param locale locale for messages
     */
    public ResourceManager(Locale locale) {
        locale = (locale == null) ? Locale.getDefault() : locale;
        this.resourceBundle = ResourceBundle.getBundle(RES_FILE_NAME, locale);
    }

    /**
     * Find message in property by key depends on selected locale
     *
     * @param key key of message in property file
     * @return localized message
     */
    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
