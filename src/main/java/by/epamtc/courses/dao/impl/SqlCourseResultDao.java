package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.CourseResult;

import java.sql.*;

/**
 * Implementation of course result dao layer
 *
 * @author DEA
 */
public class SqlCourseResultDao implements CourseResultDao {

    /**
     * SQL statement to get course result
     */
    private static final String GET_COURSE_RESULT = "SELECT cr.id, mark, comment " +
            "FROM course_results cr " +
            "JOIN user_courses uc ON cr.id = uc.course_result_id " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    /**
     * SQL statement to add new course result
     */
    private static final String CREATE_COURSE_RESULT = "INSERT INTO course_results (mark, comment) " +
            "VALUES (?, ?);";

    /**
     * SQL statement to update course result
     */
    private static final String UPDATE_COURSE_RESULT = "UPDATE course_results cr " +
            "JOIN user_courses uc " +
            "ON cr.id = uc.course_result_id " +
            "SET mark = ?, comment = ? " +
            "WHERE uc.user_id = ? " +
            "AND uc.course_id = ?;";

    /**
     * SQL statement to update course result id
     */
    private static final String UPDATE_COURSE_RESULT_ID = "UPDATE user_courses " +
            "SET course_result_id = ? " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    /**
     * Instance of connection pool
     */
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Set student's course result
     *
     * @param studentId id of student whom set result
     * @param courseId  id of course to set result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void addCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException {
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

    /**
     * Take course result for some student
     *
     * @param studentId id of student whom take result
     * @param courseId  id of course to take result
     * @return student's result at course
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public CourseResult takeCourseResult(int studentId, int courseId) throws DaoException {
        CourseResult courseResult = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSE_RESULT);
            statement.setInt(1, studentId);
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

    /**
     * Update student's course result
     *
     * @param studentId id of student whom update result
     * @param courseId  id of course to update result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws DaoException if an dao exception occurred while processing
     */
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

    /**
     * Insert course result
     *
     * @param courseResultId id of new course result
     * @param studentId      id of student whom set result
     * @param courseId       id of course to set result
     * @param connection     connection with DB
     * @throws SQLException if an SQL exception occurred while processing
     */
    private void insertCourseResult(int courseResultId, int studentId,
                                    int courseId, Connection connection) throws SQLException {
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

    /**
     * Create <code>CourseResult</code> from result set
     *
     * @param resultSet result set from sql with course result's data
     * @return <code>CourseResult</code> object from result set
     * @throws SQLException if an SQL exception occurred while processing
     */
    private CourseResult createCourseResult(ResultSet resultSet) throws SQLException {
        CourseResult course = new CourseResult();

        course.setId(resultSet.getInt(1));
        course.setMark(resultSet.getInt(2));
        course.setComment(resultSet.getString(3));

        return course;
    }
}
