package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String GET_BY_LOGIN = "SELECT users.id, login, password, surname, name, email, birthday, role " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "WHERE login = ?;";

    private static final String GET_BY_ID = "SELECT users.id, login, password, surname, name, email, birthday, role " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id " +
            "WHERE users.id = ?;";

    private static final String GET_ALL = "SELECT users.id, login, password, surname, name, email, birthday, role " +
            "FROM users " +
            "INNER JOIN user_roles ON users.role_id = user_roles.id;";

    private static final String INSERT_USER = "INSERT INTO users (login, password, surname, name, email, birthday, role_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";

    private static final String DELETE_LECTURER_FROM_COURSES = "UPDATE course_runs SET lecturer_id = NULL WHERE lecturer_id = ?;";

    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, surname = ?, name = ?, email = ?, birthday = ? WHERE id = ?";

    @Override
    public User getByLogin(String login) throws DaoException {
        User user = null;

        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get user with login " + login, e);
        }

        return user;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setSurname(resultSet.getString(4));
        user.setName(resultSet.getString(5));
        user.setEmail(resultSet.getString(6));
        user.setBirthday(resultSet.getDate(7).toLocalDate());
        user.setRole(UserRole.valueOf(resultSet.getString(8)));

        return user;
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get all users", e);
        }

        return users;
    }

    @Override
    public User getById(int id) throws DaoException {
        User user = null;

        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get user with id " + id, e);
        }

        return user;
    }

    @Override
    public void update(User ob) throws DaoException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, ob.getLogin());
            preparedStatement.setString(2, ob.getPassword());
            preparedStatement.setString(3, ob.getSurname());
            preparedStatement.setString(4, ob.getName());
            preparedStatement.setString(5, ob.getEmail());
            preparedStatement.setDate(6, Date.valueOf(ob.getBirthday()));
            preparedStatement.setInt(7, ob.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while update user " + ob.getLogin(), e);
        }
    }

    @Override
    public int insert(User ob) throws DaoException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, ob.getLogin());
            preparedStatement.setString(2, ob.getPassword());
            preparedStatement.setString(3, ob.getSurname());
            preparedStatement.setString(4, ob.getName());
            preparedStatement.setString(5, ob.getEmail());
            preparedStatement.setDate(6, Date.valueOf(ob.getBirthday()));
            preparedStatement.setInt(7, ob.getRole().getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while insert user " + ob.getLogin(), e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statUpdate = connection.prepareStatement(DELETE_LECTURER_FROM_COURSES);
            statUpdate.setInt(1, id);
            statUpdate.execute();

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            connection.commit();
        } catch (SQLException e) {
            // stub
            e.printStackTrace();

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            //log
            throw new DaoException("Error while delete user", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
