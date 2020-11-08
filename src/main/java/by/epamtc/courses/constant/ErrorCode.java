package by.epamtc.courses.constant;

/**
 * Class containing values of error code for http response
 *
 * @author DEA
 */
public final class ErrorCode {

    /**
     * Field containing code of status http 'permission denied' error response
     */
    public static final int PERMISSION_DENIED = 403;

    /**
     * Field containing code of status http 'not found' error response
     */
    public static final int NOT_FOUND = 404;

    /**
     * Private constructor to forbid creation of objects
     */
    private ErrorCode() {
    }
}
