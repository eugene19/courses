package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.CourseResult;

import java.sql.*;

public class SqlCourseResultDao implements CourseResultDao {

    private static final String GET_COURSE_RESULT = "SELECT cr.id, mark, comment " +
            "FROM course_results cr " +
            "JOIN user_courses uc ON cr.id = uc.course_result_id " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    private static final String CREATE_COURSE_RESULT = "INSERT INTO course_results (mark, comment) " +
            "VALUES (?, ?);";

    private static final String UPDATE_COURSE_RESULT_ID = "UPDATE user_courses " +
            "SET course_result_id = ? " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    private static final String UPDATE_COURSE_RESULT = "UPDATE course_results cr " +
            "JOIN user_courses uc " +
            "ON cr.id = uc.course_result_id " +
            "SET mark = ?, comment = ? " +
            "WHERE uc.user_id = ? " +
            "AND uc.course_id = ?;";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void setCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE_COURSE_RESULT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, mark);
            preparedStatement.setString(2, comment);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Error of creation course result, no ID obtained");
            }

            int idCourseResult = generatedKeys.getInt(1);
            insertCourseResult(idCourseResult, studentId, courseId, connection);
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            connectionPool.rollback(connection);
            throw new DaoException("Error while creation course result", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public CourseResult takeCourseResult(int userId, int courseId) throws DaoException {
        CourseResult courseResult = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSE_RESULT);
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                courseResult = createCourseResult(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get course result", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return courseResult;
    }

    @Override
    public void updateCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_RESULT);

            preparedStatement.setInt(1, mark);
            preparedStatement.setString(2, comment);
            preparedStatement.setInt(3, studentId);
            preparedStatement.setInt(4, courseId);

            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while update course result", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void insertCourseResult(int courseResultId, int studentId, int courseId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_RESULT_ID);
            preparedStatement.setInt(1, courseResultId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, courseId);

            preparedStatement.execute();
        } finally {
            connectionPool.closeConnection(null, preparedStatement);
        }
    }

    private CourseResult createCourseResult(ResultSet resultSet) throws SQLException {
        CourseResult course = new CourseResult();

        course.setId(resultSet.getInt(1));
        course.setMark(resultSet.getInt(2));
        course.setComment(resultSet.getString(3));

        return course;
    }
}
