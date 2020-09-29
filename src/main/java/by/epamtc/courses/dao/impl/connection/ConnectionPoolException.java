package by.epamtc.courses.dao.impl.connection;

public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = -601993984305437031L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
