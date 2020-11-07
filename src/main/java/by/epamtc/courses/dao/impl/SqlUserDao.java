package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.entity.UserRole;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of user dao layer
 *
 * @author DEA
 */
public class SqlUserDao implements UserDao {

    /**
     * SQL statement to get user by login and password
     */
    private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT u.id, " +
            "surname, name, email, birthday, role, photo_path " +
            "FROM users u " +
            "INNER JOIN user_roles ur ON u.role_id = ur.id " +
            "WHERE login = ? " +
            "AND password = ?;";

    /**
     * SQL statement to get user by id
     */
    private static final String GET_USER_BY_ID = "SELECT u.id, surname, name, " +
            "email, birthday, role, photo_path " +
            "FROM users u " +
            "INNER JOIN user_roles ur ON u.role_id = ur.id " +
            "WHERE u.id = ?;";

    /**
     * SQL statement to get all users at define course
     */
    private static final String GET_ALL_USERS_ON_COURSE = "SELECT u.id, surname, " +
            "name, email, birthday, role, photo_path, status " +
            "FROM users u " +
            "INNER JOIN user_roles ur ON u.role_id = ur.id " +
            "INNER JOIN user_courses uc ON u.id = uc.user_id " +
            "INNER JOIN user_course_statuses ucs ON uc.user_course_status_id = ucs.id " +
            "WHERE uc.course_id = ?;";

    /**
     * SQL statement to get users with define status at some course
     */
    private static final String GET_USERS_WITH_STATUS_ON_COURSE = "SELECT u.id, " +
            "surname, name, email, birthday, role, photo_path, status " +
            "FROM users u " +
            "INNER JOIN user_roles ur ON u.role_id = ur.id " +
            "INNER JOIN user_courses uc ON u.id = uc.user_id " +
            "INNER JOIN user_course_statuses ucs ON uc.user_course_status_id = ucs.id " +
            "WHERE uc.user_course_status_id = ? " +
            "AND uc.course_id = ?;";

    /**
     * SQL statement to insert new user
     */
    private static final String INSERT_USER = "INSERT INTO users (login, " +
            "password, surname, name, email, birthday, role_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    /**
     * SQL statement to update student's status on course
     */
    private static final String UPDATE_USER_COURSE_STATUS = "UPDATE user_courses " +
            "SET user_course_status_id = ? " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    /**
     * SQL statement to update user's data
     */
    private static final String UPDATE_USER = "UPDATE users SET surname = ?, " +
            "name = ?, email = ?, birthday = ?, photo_path = ? " +
            "WHERE id = ?;";

    /**
     * Instance of connection pool
     */
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Authentication of user if such user exist.
     * If user is not found return null
     *
     * @param login    entered login value
     * @param password entered password value
     * @return <code>User</code> object of user
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public User authenticate(String login, String password) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
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

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId id of course to find
     * @return list of students with statuses as <code>Map</code>
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public Map<User, UserCourseStatus> findAllStudentsOnCourse(int courseId) throws DaoException {
        Map<User, UserCourseStatus> users = new LinkedHashMap<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_USERS_ON_COURSE);
            preparedStatement.setInt(1, courseId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.put(
                        createUser(resultSet),
                        UserCourseStatus.valueOf(resultSet.getString(8))
                );
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while find all users on course with id " + courseId, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return users;
    }

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId id of course to find
     * @param status   value of status to find
     * @return list of students with statuses as <code>Map</code>
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public Map<User, UserCourseStatus> findStudentsOnCourseWithStatus(int courseId,
                                                                      UserCourseStatus status
    ) throws DaoException {
        Map<User, UserCourseStatus> users = new LinkedHashMap<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USERS_WITH_STATUS_ON_COURSE);
            preparedStatement.setInt(1, status.getId());
            preparedStatement.setInt(2, courseId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.put(
                        createUser(resultSet),
                        UserCourseStatus.valueOf(resultSet.getString(8))
                );
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while find users with status '" +
                    status + "' on course with id " + courseId, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return users;
    }

    /**
     * Find user by id. If user is not found return null
     *
     * @param userId id of user to find
     * @return <code>User</code> object of user
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public User findUserById(int userId) throws DaoException {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while find user by id " + userId, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return user;
    }

    /**
     * Register (add) new user
     *
     * @param user user's data
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void register(UserAuthData user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(7, user.getRole().getId());

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while register user " + user.getLogin(), e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Update user's data
     *
     * @param user object <code>User</code> of client
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);

            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            preparedStatement.setString(5, user.getPhotoPath());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while update user", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Update student status on course
     *
     * @param userId   id of student to update
     * @param courseId id of course to update
     * @param status   new student's status
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void updateUserCourseStatus(int userId, int courseId,
                                       UserCourseStatus status) throws DaoException {
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

    /**
     * Create <code>User</code> from result set
     *
     * @param resultSet result set from sql with user's data
     * @return <code>User</code> object from result set
     * @throws SQLException if an SQL exception occurred while processing
     */
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
}
