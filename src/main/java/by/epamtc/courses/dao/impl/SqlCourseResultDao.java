package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.CourseResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCourseResultDao implements CourseResultDao {

    private static final String GET_COURSE_RESULT_FOR_USER = "SELECT course_results.id, mark, comment " +
            "FROM course_results " +
            "JOIN user_courses ON course_results.id = user_courses.course_result_id " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws DaoException {
        CourseResult courseResult = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSE_RESULT_FOR_USER);
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                courseResult = createCourseResult(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get courseResult by id: " + courseId, e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return courseResult;
    }

    private CourseResult createCourseResult(ResultSet resultSet) throws SQLException {
        CourseResult course = new CourseResult();

        course.setId(resultSet.getInt(1));
        course.setMark(resultSet.getInt(2));
        course.setComment(resultSet.getString(3));

        return course;
    }
}
