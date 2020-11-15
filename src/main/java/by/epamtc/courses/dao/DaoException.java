package by.epamtc.courses.dao;

/**
 * The class responsible for errors that occurred in the DAO layer
 *
 * @author DEA
 */
public class DaoException extends Exception {
    private static final long serialVersionUID = -7628486779139736741L;

    /**
     * Constructs a new DaoException with the specified message
     *
     * @param message a String specifying the text of the exception message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs a new DaoException with the specified detail message and cause
     *
     * @param message a String specifying the text of the exception message
     * @param cause   the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
