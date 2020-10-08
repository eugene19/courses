package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCourseDao implements CourseDao {
    private static final Logger LOGGER = Logger.getLogger(SqlCourseDao.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_ALL_COURSES = "SELECT courses.id, summary, description, " +
            "start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id;";

    private static final String CREATE_COURSE = "INSERT INTO courses (summary, description, " +
            "students_limit) VALUES (?, ?, ?);";

    private static final String INSERT_COURSE_STATUS = "INSERT INTO course_runs (course_id, " +
            "start_date, end_date, lecturer_id, status_id) VALUES (?, ?, ?, ?, ?)";

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

    @Override
    public void createNew(Course course) throws DaoException {
        Connection connection = null;
        PreparedStatement statementCourse = null;
        PreparedStatement statementCourseRun = null;

        try {
            connection = connectionPool.takeConnection();
            statementCourse = connection.prepareStatement(CREATE_COURSE, Statement.RETURN_GENERATED_KEYS);

            statementCourse.setString(1, course.getSummary());
            statementCourse.setString(2, course.getDescription());
            statementCourse.setInt(3, course.getStudentsLimit());

            int affectedRows = statementCourse.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Error of creation course, no rows affected");
            }

            ResultSet generatedKeys = statementCourse.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idNewCourse = generatedKeys.getInt(1);
                statementCourseRun = connection.prepareStatement(INSERT_COURSE_STATUS);

                statementCourseRun.setInt(1, idNewCourse);
                statementCourseRun.setDate(2, Date.valueOf(course.getStartDate()));
                statementCourseRun.setDate(3, Date.valueOf(course.getEndDate()));
                statementCourseRun.setInt(4, course.getLecturerId());
                statementCourseRun.setInt(5, course.getStatus().getId());

                statementCourseRun.execute();
            } else {
                throw new DaoException("Error of creation course, no ID obtained");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while insert course", e);
        } finally {
            if (statementCourseRun != null) {
                try {
                    statementCourseRun.close();
                } catch (SQLException e) {
                    LOGGER.error("Error while closing prepare statement");
                }
            }
            connectionPool.closeConnection(connection, statementCourse);
        }
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
