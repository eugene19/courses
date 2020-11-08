package by.epamtc.courses.dao.impl.connection;

/**
 * Class containing constants with keys of DB property
 *
 * @author DEA
 */
public final class DBParameter {

    /**
     * Key of property 'Name of DB driver'
     */
    public static final String DB_DRIVER = "db.driver";

    /**
     * Key of property 'DB's url'
     */
    public static final String DB_URL = "db.url";

    /**
     * Key of property 'User name'
     */
    public static final String DB_USER = "db.user";

    /**
     * Key of property 'User password'
     */
    public static final String DB_PASSWORD = "db.password";

    /**
     * Key of property 'Size of pool'
     */
    public static final String DB_POLL_SIZE = "db.poolSize";

    /**
     * Private constructor to forbid creation of objects
     */
    private DBParameter() {
    }
}
