package by.epamtc.courses.dao.impl.connection;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Class for storing connections
 *
 * @author DEA
 */
public final class ConnectionPool {
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    /**
     * ConnectionPool instance
     */
    private static final ConnectionPool instance = new ConnectionPool();

    /**
     * Free connections queue
     */
    private BlockingQueue<Connection> connectionQueue;

    /**
     * Given away connections queue
     */
    private BlockingQueue<Connection> givenAwayConQueue;

    /**
     * Name of DB
     */
    private final String driverName;

    /**
     * URL of DB
     */
    private final String url;

    /**
     * User name from DB
     */
    private final String user;

    /**
     * User passwords from DB
     */
    private final String password;

    /**
     * Count of connections in pool
     */
    private int poolSize;

    /**
     * Construct a Connection pool
     */
    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager
                    .getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
            logger.warn("Error while reading pool size value from file. " +
                    "Set poll size = " + poolSize, e);
        }

        initPoolData();
    }

    /**
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Initialize all connection in the pool
     */
    private void initPoolData() {
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(
                        url,
                        user,
                        password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);
        }
    }

    /**
     * Return connection from pool if there is free connection
     *
     * @return connection from pool
     * @throws ConnectionPoolException if an InterruptedException error has
     *                                 occurred during the waiting of free connection
     */
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    /**
     * Close Statement and Connection if they is not null
     *
     * @param con object of <code>Connection</code>
     * @param st  object of <code>Statement</code>
     */
    public void closeConnection(Connection con, Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.error("Statement isn't closed");
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Connection isn't return to the pool");
            }
        }
    }

    /**
     * Rollback changes in are made in connection
     *
     * @param connection object of <code>Connection</code>
     */
    public void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Error while rollback connection", e);
            }
        }
    }

    /**
     * Close all connections in queue
     *
     * @param queue queue of connections
     * @throws SQLException if an SQL related error has occurred during the processing
     */
    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    /**
     * Close all connections from free and given away connections
     */
    public void dispose() {
        clearConnectionQueue();
    }

    /**
     * Close all connections from free and given away connections
     */
    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(connectionQueue);
            closeConnectionsQueue(givenAwayConQueue);
        } catch (SQLException e) {
            logger.error("Error closing the connection", e);
        }
    }

    /**
     * Class-wrapper on <code>PooledConnection</code> for work in pool
     */
    private class PooledConnection implements Connection {

        /**
         * Instance of Connection
         */
        private final Connection connection;

        /**
         * Construct PooledConnection above Connection
         *
         * @param connection instance of real connection
         * @throws SQLException if an SQL related error has occurred during
         *                      the processing
         */
        public PooledConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        /**
         * Really close connection
         *
         * @throws SQLException if an SQL related error has occurred during
         *                      closing connection
         */
        public void reallyClose() throws SQLException {
            connection.close();
        }

        /**
         * Transfer connection from given away queue to free queue
         *
         * @throws SQLException if an SQL related error has occurred during the processing
         */
        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            connection.setAutoCommit(true);

            if (!givenAwayConQueue.remove(this)) {
                throw new SQLException(
                        "Error deleting connection from the given away connections pool");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error allocating connection in the pool");
            }
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements)
                throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType,
                                         int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType,
                    resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int resultSetType,
                                         int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes)
                throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public void setClientInfo(Properties arg0)
                throws SQLClientInfoException {
            connection.setClientInfo(arg0);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {

            return connection.getMetaData();
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
            connection.setTypeMap(arg0);
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType,
                                             int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType,
                                             int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.prepareCall(sql, resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void setClientInfo(String name, String value)
                throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public boolean isWrapperFor(Class<?> face) throws SQLException {
            return connection.isWrapperFor(face);
        }

        @Override
        public <T> T unwrap(Class<T> face) throws SQLException {
            return connection.unwrap(face);
        }

        @Override
        public void abort(Executor arg0) throws SQLException {
            connection.abort(arg0);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void setSchema(String arg0) throws SQLException {
            connection.setSchema(arg0);
        }

        @Override
        public void releaseSavepoint(Savepoint arg0) throws SQLException {
            connection.releaseSavepoint(arg0);
        }

        @Override
        public void rollback(Savepoint arg0) throws SQLException {
            connection.rollback(arg0);
        }

        @Override
        public void setNetworkTimeout(Executor arg0, int arg1)
                throws SQLException {
            connection.setNetworkTimeout(arg0, arg1);
        }
    }
}
