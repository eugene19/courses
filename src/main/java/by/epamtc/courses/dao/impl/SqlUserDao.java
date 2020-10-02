package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserRole;

import java.sql.*;

public class SqlUserDao implements UserDao {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_BY_LOGIN_AND_PASSWORD = "SELECT users.id, surname, name, email, birthday, role " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "WHERE login = ? " +
            "AND password = ?;";

    private static final String REGISTER_USER = "INSERT INTO users (login, password, surname, name, email, birthday, role_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    @Override
    public User authenticate(String login, String password) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while authenticate user " + login, e);
        } finally {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public boolean register(UserAuthData user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_USER);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(7, user.getRole().getId());

            return preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while insert user " + user.getLogin(), e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt(1));
        user.setSurname(resultSet.getString(2));
        user.setName(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setBirthday(resultSet.getDate(5).toLocalDate());
        user.setRole(UserRole.valueOf(resultSet.getString(6)));

        return user;
    }
}
