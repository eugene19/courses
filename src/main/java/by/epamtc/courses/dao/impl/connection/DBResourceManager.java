package by.epamtc.courses.dao.impl.connection;

import java.util.ResourceBundle;

public class DBResourceManager {
    private static final DBResourceManager instance = new DBResourceManager();
    private static final String RES_FILE_NAME = "db";

    private ResourceBundle bundle = ResourceBundle.getBundle(RES_FILE_NAME);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
