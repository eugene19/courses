package by.epamtc.courses.service;

/**
 * The class responsible for errors that occurred in the Service layer
 *
 * @author DEA
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = -4344599235727593620L;

    /**
     * Constructs a new ServiceException with the specified cause
     *
     * @param cause the cause which is saved for later retrieval by the Throwable.getCause() method
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
