package by.epamtc.courses.dao.impl.connection;

import java.util.ResourceBundle;

/**
 * Class for getting values of DB property
 *
 * @author DEA
 */
public class DBResourceManager {

    /**
     * Property file name which contains DB parameters
     */
    private static final String RES_FILE_NAME = "database/db";

    /**
     * DB resource manager instance
     */
    private static final DBResourceManager instance = new DBResourceManager();

    /**
     * Object of <code>ResourceBundle</code> which will take properties
     */
    private final ResourceBundle bundle = ResourceBundle.getBundle(RES_FILE_NAME);

    /**
     * Construct a DBResourceManager
     */
    private DBResourceManager() {
    }

    /**
     * @return instance of ServiceProvider
     */
    public static DBResourceManager getInstance() {
        return instance;
    }

    /**
     * Find value of property by key
     *
     * @param key key of DB property
     * @return value of DB property
     */
    public String getValue(String key) {
        return bundle.getString(key);
    }
}
