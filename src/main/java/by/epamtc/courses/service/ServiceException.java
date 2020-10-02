package by.epamtc.courses.service;

public class ServiceException extends Exception {
    private static final long serialVersionUID = -4344599235727593620L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
