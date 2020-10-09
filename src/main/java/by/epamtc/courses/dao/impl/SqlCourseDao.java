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

    private static final String GET_COURSE_BY_ID = "SELECT courses.id, summary, description, " +
            "start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id " +
            "WHERE courses.id = ?;";

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
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE_COURSE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, course.getSummary());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getStudentsLimit());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Error of creation course, no rows affected");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idNewCourse = generatedKeys.getInt(1);
                insertCourseRun(idNewCourse, course, connection);
            } else {
                throw new DaoException("Error of creation course, no ID obtained");
            }
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException("Error while insert course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Course getCourse(int courseId) throws DaoException {
        Course course = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSE_BY_ID);
            statement.setInt(1, courseId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = createCourse(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get course by id: " + courseId, e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return course;
    }

    private void insertCourseRun(int courseId, Course course, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT_COURSE_STATUS);
            preparedStatement.setInt(1, courseId);
            preparedStatement.setDate(2, Date.valueOf(course.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(course.getEndDate()));
            preparedStatement.setInt(4, course.getLecturerId());
            preparedStatement.setInt(5, course.getStatus().getId());

            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error("Error while closing prepare statement");
                }
            }
        }
    }

    private void rollback(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException("Error while rollback connection", e);
            }
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
