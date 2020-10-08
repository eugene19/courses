package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlCourseDao implements CourseDao {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_ALL_COURSES = "SELECT courses.id, summary, description, " +
            "start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id;";

    @Override
    public List<Course> takeAllCourses() throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_COURSES);

            while (resultSet.next()) {
                courses.add(createCourse(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get all courses", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return courses;
    }

    private Course createCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt(1));
        course.setSummary(resultSet.getString(2));
        course.setDescription(resultSet.getString(3));
        course.setStartDate(resultSet.getDate(4).toLocalDate());
        course.setEndDate(resultSet.getDate(5).toLocalDate());
        course.setStudentsLimit(resultSet.getInt(6));
        course.setLecturerId(resultSet.getInt(7));
        course.setStatus(CourseStatus.valueOf(resultSet.getString(8)));

        return course;
    }
}
