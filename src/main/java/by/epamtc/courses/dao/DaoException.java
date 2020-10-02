package by.epamtc.courses.dao;

public class DaoException extends Exception {
    private static final long serialVersionUID = -7628486779139736741L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
