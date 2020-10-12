package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.entity.UserRole;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(SqlUserDao.class);
    private static final String ALGORITHM_NAME = "MD5";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_BY_LOGIN_AND_PASSWORD = "SELECT users.id, surname, name, email, birthday, role, photo_path " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "WHERE login = ? " +
            "AND password = ?;";

    private static final String GET_BY_ID = "SELECT users.id, surname, name, email, birthday, role, photo_path " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "WHERE users.id = ?;";

    private static final String REGISTER_USER = "INSERT INTO users (login, password, surname, name, email, birthday, role_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String EDIT_USER = "UPDATE users SET surname = ?, name = ?, email = ?, birthday = ?, photo_path = ? " +
            "WHERE id = ?;";

    private static final String GET_USERS_ON_COURSE = "SELECT users.id, surname, name, email, birthday, role, photo_path, status " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "INNER JOIN user_courses ON users.id = user_courses.user_id " +
            "INNER JOIN user_course_statuses ucs ON user_courses.user_course_status_id = ucs.id " +
            "WHERE course_id = ?;";

    private static final String UPDATE_USER_COURSE_STATUS = "UPDATE user_courses SET user_course_status_id = ? " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

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
            preparedStatement.setString(2, hashPassword(password));

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
    public void register(UserAuthData user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_USER);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, hashPassword(user.getPassword()));
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(7, user.getRole().getId());

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while insert user " + user.getLogin(), e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(EDIT_USER);

            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(5, user.getPhotoPath());
            preparedStatement.setInt(6, user.getId());

            return preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while update user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Map<User, UserCourseStatus> getUserOnCourse(int courseId) throws DaoException {
        Map<User, UserCourseStatus> users = new LinkedHashMap<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USERS_ON_COURSE);

            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.put(
                        createUser(resultSet),
                        UserCourseStatus.valueOf(resultSet.getString(8))
                );
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get all courses", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return users;
    }

    @Override
    public void updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_COURSE_STATUS);

            preparedStatement.setInt(1, status.getId());
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, courseId);

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while update user course status", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public User getUserById(int userId) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while getting user by id", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return user;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt(1));
        user.setSurname(resultSet.getString(2));
        user.setName(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setBirthday(resultSet.getDate(5).toLocalDate());
        user.setRole(UserRole.valueOf(resultSet.getString(6)));
        user.setPhotoPath(resultSet.getString(7));

        return user;
    }

    private String hashPassword(String password) {
        byte[] digest = new byte[0];

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_NAME);
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Wrong hash algorithm.");
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

        while (md5Hex.length() < 32) {
            md5Hex.insert(0, "0");
        }

        return md5Hex.toString();
    }
}
