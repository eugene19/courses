package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.UserCourseStatus;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;


public class SqlCourseDaoTest {

    private final CourseDao courseDao = new SqlCourseDao();

    @Test(timeout = 100)
    public void addStudentApplicationOnCourse() throws DaoException, SQLException {
        int studentId = 1;
        int courseId = 1;

        courseDao.addStudentApplicationOnCourse(studentId, courseId);

        boolean hasApplicationInDB = hasApplicationInDB(studentId, courseId);
        assertTrue("Application is not been added", hasApplicationInDB);
    }

    @Test(timeout = 100)
    public void findCoursesWithResultsForStudent() throws DaoException {
        int studentId = 2;
        List<Course> courses = courseDao.findAllCoursesWithResultsForStudent(studentId);

        assertEquals("Wrong count of courses with results for student " + studentId,
                2, courses.size());
    }

    @Test(timeout = 100)
    public void findCoursesWithResultsForNotExistingStudent() throws DaoException {
        int notValidStudentId = -1;
        List<Course> courses = courseDao.findAllCoursesWithResultsForStudent(notValidStudentId);

        assertEquals("Wrong count of courses with results for student " + notValidStudentId,
                0, courses.size());
    }

    @Test(timeout = 100)
    public void findValidCourseById() throws DaoException {
        int courseId = 1;
        Course courseById = courseDao.findCourseById(courseId);

        assertNotNull("There is no valid course", courseById);
    }

    @Test(timeout = 100)
    public void findNotExistingCourseById() throws DaoException {
        int courseId = -1;
        Course courseById = courseDao.findCourseById(courseId);

        assertNull("There is not existing course", courseById);
    }

    private boolean hasApplicationInDB(int studentId, int courseId) throws SQLException {
        String sqlFindApplication = "select * from user_courses where user_id = ? " +
                "and course_id = ? and user_course_status_id = ?;";

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindApplication)
        ) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, UserCourseStatus.APPLIED.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
    }
}
