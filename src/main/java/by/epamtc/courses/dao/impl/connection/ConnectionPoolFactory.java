package by.epamtc.courses.dao.impl.connection;

import org.apache.log4j.Logger;

public final class ConnectionPoolFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolFactory.class);

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
                LOGGER.debug("Start initializing connection pool.");
                connectionPool.initPoolData();
                isPoolInitialized = true;
            } catch (ConnectionPoolException e) {
                LOGGER.error("Error while initializing connection pool.", e);
            }
        }

        return connectionPool;
    }
}
