package by.epamtc.courses.dao.impl.connection;

/**
 * The class responsible for errors that occurred in Connection pool
 *
 * @author DEA
 */
public class ConnectionPoolException extends RuntimeException {
    private static final long serialVersionUID = -601993984305437031L;

    /**
     * Construct a ConnectionPoolException
     */
    public ConnectionPoolException() {
    }

    /**
     * Constructs a new ConnectionPoolException with the specified message
     *
     * @param message a String specifying the text of the exception message
     */
    public ConnectionPoolException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConnectionPoolException with the specified detail message and cause
     *
     * @param message a String specifying the text of the exception message
     * @param cause   the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ConnectionPoolException with the specified cause
     *
     * @param cause the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
