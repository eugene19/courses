package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.entity.CourseResult;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SqlCourseResultDaoTest {

    private final CourseResultDao courseResultDao = new SqlCourseResultDao();

    @Test(timeout = 100)
    public void setCourseResult() throws DaoException, SQLException {
        int studentId = 1;
        int courseId = 4;
        int mark = 2;
        String comment = "test comment";

        courseResultDao.addCourseResult(studentId, courseId, mark, comment);

        boolean hasResultInDB = hasResultInDB(studentId, courseId);
        assertTrue("Result is not been added", hasResultInDB);
    }

    @Test(timeout = 100)
    public void takeCourseResult() throws DaoException {
        int studentId = 2;
        int courseId = 5;
        int expectedMark = 10;
        String expectedComment = "Отличная работа! Все задания выполнял на отлично.";

        CourseResult result = courseResultDao.takeCourseResult(studentId, courseId);

        assertNotNull("Result is absent", result);
        assertEquals("Wrong mark of result", expectedMark, result.getMark());
        assertEquals("Wrong comment of result", expectedComment, result.getComment());
    }

    @Test(timeout = 100)
    public void takeNotExistingCourseResult() throws DaoException {
        int notExistingStudentId = -1;
        int notExistingCourseId = -1;

        CourseResult result = courseResultDao.takeCourseResult(notExistingStudentId, notExistingCourseId);

        assertNull("Not existing result is present", result);
    }

    @Test(timeout = 100)
    public void updateCourseResult() throws DaoException, SQLException {
        int studentId = 3;
        int courseId = 5;
        int mark = 1;
        String comment = "new comment";

        courseResultDao.updateCourseResult(studentId, courseId, mark, comment);

        CourseResult result = takeCourseResult(studentId, courseId);
        assertEquals("Mark is not update", mark, result.getMark());
        assertEquals("Comment is not update", comment, result.getComment());
    }

    private CourseResult takeCourseResult(int studentId, int courseId) throws SQLException {
        String sqlFindResult = "SELECT cr.id, mark, comment " +
                "FROM course_results cr " +
                "JOIN user_courses uc ON cr.id = uc.course_result_id " +
                "WHERE user_id = ? " +
                "AND course_id = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        CourseResult result = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(sqlFindResult);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = new CourseResult();
                result.setMark(resultSet.getInt(2));
                result.setComment(resultSet.getString(3));
            }

            return result;
        } finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private boolean hasResultInDB(int studentId, int courseId) throws SQLException {
        String sqlFindResult = "select course_result_id from user_courses where user_id = ? " +
                "and course_id = ? and course_result_id is not null;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(sqlFindResult);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
        }
    }
}
