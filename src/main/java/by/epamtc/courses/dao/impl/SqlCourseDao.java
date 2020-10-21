package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCourseDao implements CourseDao {

    private static final String ADD_STUDENT_APPLY_ON_COURSE = "INSERT INTO user_courses " +
            "(user_id, course_id, user_course_status_id) " +
            "VALUES (?, ?, ?);";

    private static final String CREATE_COURSE = "INSERT INTO courses " +
            "(summary, description, students_limit, materials_path) " +
            "VALUES (?, ?, ?, ?);";

    private static final String INSERT_COURSE_RUN = "INSERT INTO course_runs " +
            "(course_id, start_date, end_date, lecturer_id, status_id) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ALL_COURSES = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "ORDER BY c.id DESC;";

    private static final String GET_COURSES_WITH_STATUS = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "WHERE cr.status_id = ? " +
            "ORDER BY c.id DESC;";

    private static final String GET_COURSES_WITH_RESULTS_FOR_STUDENT = "SELECT c.id, summary, " +
            "description, materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "LEFT JOIN user_courses uc ON c.id = uc.course_id " +
            "WHERE course_result_id IS NOT NULL " +
            "AND user_id = ? " +
            "ORDER BY c.id DESC;";

    private static final String GET_COURSE_BY_ID = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "WHERE c.id = ?;";

    // TODO: 10/21/20 Заменить на 1 команду (с джоином)
    private static final String UPDATE_COURSE = "UPDATE courses " +
            "SET summary = ?, description = ?, students_limit = ? " +
            "WHERE id = ?;";

    private static final String UPDATE_COURSE_RUN = "UPDATE course_runs " +
            "SET start_date = ?, end_date = ?, status_id = ? " +
            "WHERE course_id = ?;";

    private static final String GET_USER_COURSE_STATUS = "SELECT status " +
            "FROM user_course_statuses ucs " +
            "JOIN user_courses uc ON ucs.id = uc.user_course_status_id " +
            "WHERE uc.user_id = ? " +
            "AND uc.course_id = ?;";

    private static final String LEAVE_USER_FROM_COURSE = "DELETE FROM user_courses " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    private static final String UPDATE_COURSE_STATUS = "UPDATE course_runs " +
            "SET status_id = ? " +
            "WHERE course_id = ?;";

    private static final String UPDATE_COURSE_MATERIAL_PATH = "UPDATE courses " +
            "SET materials_path = ? " +
            "WHERE id = ?;";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addStudentApplicationOnCourse(int userId, int courseId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_STUDENT_APPLY_ON_COURSE);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, UserCourseStatus.APPLIED.getId());

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while add student's application on course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void create(Course course) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE_COURSE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, course.getSummary());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getStudentsLimit());
            preparedStatement.setString(4, course.getMaterialPath());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Error of creation course, no ID obtained");
            }

            int idNewCourse = generatedKeys.getInt(1);
            course.setId(idNewCourse);

            insertCourseRun(course, connection);
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            connectionPool.rollback(connection);
            throw new DaoException("Error while create course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Course> findAllCourses() throws DaoException {
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
    public List<Course> findCoursesWithStatus(CourseStatus status) throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_COURSES_WITH_STATUS);
            preparedStatement.setInt(1, status.getId());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(createCourse(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get courses with status " + status, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return courses;
    }

    @Override
    public Course findCourseById(int courseId) throws DaoException {
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

    @Override
    public List<Course> findAllCoursesWithResultsForStudent(int userId) throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSES_WITH_RESULTS_FOR_STUDENT);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(createCourse(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get courses with results for student", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return courses;
    }

    @Override
    public void leaveStudentFromCourse(int userId, int courseId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(LEAVE_USER_FROM_COURSE);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while user leave from course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws DaoException {
        UserCourseStatus userCourseStatus = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_USER_COURSE_STATUS);
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userCourseStatus = UserCourseStatus.valueOf(resultSet.getString(1));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get user course status", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }

        return userCourseStatus;
    }

    @Override
    public void update(Course course) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_COURSE);

            preparedStatement.setString(1, course.getSummary());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getStudentsLimit());
            preparedStatement.setInt(4, course.getId());

            preparedStatement.executeUpdate();
            updateCourseRun(course, connection);
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            connectionPool.rollback(connection);
            throw new DaoException("Error while updating course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void updateCourseMaterialPath(int courseId, String fileName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_MATERIAL_PATH);

            preparedStatement.setString(1, fileName);
            preparedStatement.setInt(2, courseId);

            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while updating course material path", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void updateStatus(int courseId, CourseStatus courseStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_STATUS);

            preparedStatement.setInt(1, courseStatus.getId());
            preparedStatement.setInt(2, courseId);

            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while updating course status", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private void insertCourseRun(Course course, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT_COURSE_RUN);

            preparedStatement.setInt(1, course.getId());
            preparedStatement.setDate(2, Date.valueOf(course.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(course.getEndDate()));
            preparedStatement.setInt(4, course.getLecturerId());
            preparedStatement.setInt(5, course.getStatus().getId());

            preparedStatement.execute();
        } finally {
            connectionPool.closeConnection(null, preparedStatement);
        }
    }

    private void updateCourseRun(Course course, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_RUN);

            preparedStatement.setDate(1, Date.valueOf(course.getStartDate()));
            preparedStatement.setDate(2, Date.valueOf(course.getEndDate()));
            preparedStatement.setInt(3, course.getStatus().getId());
            preparedStatement.setInt(4, course.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.closeConnection(null, preparedStatement);
        }
    }

    private Course createCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt(1));
        course.setSummary(resultSet.getString(2));
        course.setDescription(resultSet.getString(3));
        course.setMaterialPath(resultSet.getString(4));
        course.setStartDate(resultSet.getDate(5).toLocalDate());
        course.setEndDate(resultSet.getDate(6).toLocalDate());
        course.setStudentsLimit(resultSet.getInt(7));
        course.setLecturerId(resultSet.getInt(8));
        course.setStatus(CourseStatus.valueOf(resultSet.getString(9)));

        return course;
    }
}
