package by.epamtc.courses.service;

/**
 * The class responsible for errors that occurred in the Service layer
 *
 * @author DEA
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = -4344599235727593620L;

    /**
     * Construct a DaoException
     */
    public ServiceException() {
    }

    /**
     * Constructs a new ServiceException with the specified message
     *
     * @param message a String specifying the text of the exception message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServiceException with the specified detail message and cause
     *
     * @param message a String specifying the text of the exception message
     * @param cause   the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ServiceException with the specified cause
     *
     * @param cause the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
