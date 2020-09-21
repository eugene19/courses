package by.epamtc.courses.dao.impl.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static final String DATASOURCE_NAME = "java:comp/env/jdbc/courses";
    private static DataSource dataSource;

    static {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            //log
            e.printStackTrace();
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
