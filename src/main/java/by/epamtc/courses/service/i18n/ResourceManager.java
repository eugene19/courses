package by.epamtc.courses.service.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
    private static final String RES_FILE_NAME = "strings";

    private ResourceBundle resourceBundle;

    public ResourceManager(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(RES_FILE_NAME, locale);
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
