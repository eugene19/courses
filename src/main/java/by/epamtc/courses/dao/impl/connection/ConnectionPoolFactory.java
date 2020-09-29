package by.epamtc.courses.dao.impl.connection;

public final class ConnectionPoolFactory {
    private static final ConnectionPoolFactory instance = new ConnectionPoolFactory();
    private final ConnectionPool connectionPool = new ConnectionPool();

    private boolean isPoolInitialized = false;

    private ConnectionPoolFactory() {
    }

    public static ConnectionPoolFactory getInstance() {
        return instance;
    }

    public ConnectionPool getConnectionPool() {
        if (!isPoolInitialized) {
            try {
                connectionPool.initPoolData();
                isPoolInitialized = true;
            } catch (ConnectionPoolException e) {
                //log
                e.printStackTrace();
            }
        }
        return connectionPool;
    }
}
