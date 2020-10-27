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

/**
 * Implementation of course dao layer
 *
 * @author DEA
 */
public class SqlCourseDao implements CourseDao {

    /**
     * SQL statement to add student's application
     */
    private static final String ADD_STUDENT_APPLY_ON_COURSE = "INSERT INTO user_courses " +
            "(user_id, course_id, user_course_status_id) " +
            "VALUES (?, ?, ?);";

    /**
     * SQL statement to add new course
     */
    private static final String CREATE_COURSE = "INSERT INTO courses " +
            "(summary, description, students_limit, materials_path) " +
            "VALUES (?, ?, ?, ?);";

    /**
     * SQL statement to insert course's data
     */
    private static final String INSERT_COURSE_RUN = "INSERT INTO course_runs " +
            "(course_id, start_date, end_date, lecturer_id, status_id) " +
            "VALUES (?, ?, ?, ?, ?)";

    /**
     * SQL statement to get part of courses with sorting
     */
    private static final String GET_COURSES_FOR_PAGE_SORTED = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "ORDER BY %s " +
            "LIMIT ? OFFSET ?;";

    /**
     * SQL statement to get courses with status
     */
    private static final String GET_COURSES_WITH_STATUS = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "WHERE cs.status IN (%s) " +
            "ORDER BY %s " +
            "LIMIT ? OFFSET ?;";

    /**
     * SQL statement to get courses for which student has result
     */
    private static final String GET_COURSES_WITH_RESULTS_FOR_STUDENT = "SELECT c.id, summary, " +
            "description, materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "LEFT JOIN user_courses uc ON c.id = uc.course_id " +
            "WHERE course_result_id IS NOT NULL " +
            "AND user_id = ?;";

    /**
     * SQL statement to get course by id
     */
    private static final String GET_COURSE_BY_ID = "SELECT c.id, summary, description, " +
            "materials_path, start_date, end_date, students_limit, lecturer_id, status " +
            "FROM courses c " +
            "LEFT JOIN course_runs cr ON c.id = cr.course_id " +
            "LEFT JOIN course_statuses cs ON cr.status_id = cs.id " +
            "WHERE c.id = ?;";

    /**
     * SQL statement to update course
     */
    private static final String UPDATE_COURSE = "UPDATE courses c " +
            "JOIN course_runs cr on c.id = cr.course_id " +
            "SET summary = ?, description = ?, students_limit = ?, start_date = ?, " +
            "end_date = ?, status_id = ? " +
            "WHERE c.id = ?;";

    /**
     * SQL statement to get student's status at course
     */
    private static final String GET_USER_COURSE_STATUS = "SELECT status " +
            "FROM user_course_statuses ucs " +
            "JOIN user_courses uc ON ucs.id = uc.user_course_status_id " +
            "WHERE uc.user_id = ? " +
            "AND uc.course_id = ?;";

    /**
     * SQL statement to leave student from course
     */
    private static final String LEAVE_USER_FROM_COURSE = "DELETE FROM user_courses " +
            "WHERE user_id = ? " +
            "AND course_id = ?;";

    /**
     * SQL statement to update course's status
     */
    private static final String UPDATE_COURSE_STATUS = "UPDATE course_runs " +
            "SET status_id = ? " +
            "WHERE course_id = ?;";

    /**
     * SQL statement to update course's material path
     */
    private static final String UPDATE_COURSE_MATERIAL_PATH = "UPDATE courses " +
            "SET materials_path = ? " +
            "WHERE id = ?;";

    /**
     * Instance of connection pool
     */
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Add student's application on course
     *
     * @param studentId id of student who apply
     * @param courseId  id of course to apply
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void addStudentApplicationOnCourse(int studentId, int courseId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_STUDENT_APPLY_ON_COURSE);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, UserCourseStatus.APPLIED.getId());

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while add student's application on course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Create (add) new course
     *
     * @param course entity of <code>Course</code> object
     * @throws DaoException if an dao exception occurred while processing
     */
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

    /**
     * Find list of courses with their results as <code>Map</code>
     * for student
     *
     * @param studentId id of student to find
     * @return <code>Map</code> of courses with results for student
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public List<Course> findAllCoursesWithResultsForStudent(int studentId) throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_COURSES_WITH_RESULTS_FOR_STUDENT);
            statement.setInt(1, studentId);

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

    /**
     * Find course by identifier
     *
     * @param courseId id of course to find
     * @return object of <code>Course</code> entity or null if such course does not exist
     * @throws DaoException if an dao exception occurred while processing
     */
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

    /**
     * Find part of courses list
     *
     * @param count  limit of courses
     * @param offset offset of courses
     * @param sort   name of column to sort list
     * @return <code>List</code> of courses
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public List<Course> findCoursesForPage(int count, int offset, String sort) throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(
                    String.format(GET_COURSES_FOR_PAGE_SORTED, sort));

            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                courses.add(createCourse(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get all courses for page", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return courses;
    }

    /**
     * Find list of courses of define status
     *
     * @param statuses value of status to find
     * @param count    limit of courses
     * @param offset   offset of courses
     * @param sort     name of column to sort list
     * @return List of courses with define status
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public List<Course> findCoursesWithStatusForPage(String statuses, int count,
                                                     int offset, String sort) throws DaoException {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(
                    String.format(GET_COURSES_WITH_STATUS, statuses, sort));

            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(createCourse(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while get courses with statuses " + statuses, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return courses;
    }

    /**
     * Left student from course
     *
     * @param studentId id of student to leave
     * @param courseId  id of course from which should leave
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void leaveStudentFromCourse(int studentId, int courseId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(LEAVE_USER_FROM_COURSE);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while user leave from course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Take students status
     *
     * @param userId   id of student to take status
     * @param courseId id of course to take status
     * @return <code>UserCourseStatus</code> object which contain student's status
     * @throws DaoException if an dao exception occurred while processing
     */
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

    /**
     * Update course's data
     *
     * @param course object <code>Course</code> of course to update
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void update(Course course) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COURSE);

            preparedStatement.setString(1, course.getSummary());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getStudentsLimit());
            preparedStatement.setDate(4, Date.valueOf(course.getStartDate()));
            preparedStatement.setDate(5, Date.valueOf(course.getEndDate()));
            preparedStatement.setInt(6, course.getStatus().getId());
            preparedStatement.setInt(7, course.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error while updating course", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Update course's material path
     *
     * @param courseId id of course to update
     * @param fileName new value of file name
     * @throws DaoException if an dao exception occurred while processing
     */
    @Override
    public void updateMaterialPath(int courseId, String fileName) throws DaoException {
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

    /**
     * Update course's status
     *
     * @param courseId     id of course to update
     * @param courseStatus new value of course status
     * @throws DaoException if an dao exception occurred while processing
     */
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

    /**
     * Add new course run
     *
     * @param course     course's data
     * @param connection connection with DB
     * @throws SQLException if an SQL exception occurred while processing
     */
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

    /**
     * Create <code>Course</code> from result set
     *
     * @param resultSet result set from sql with course's data
     * @return <code>Course</code> object from result set
     * @throws SQLException if an SQL exception occurred while processing
     */
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
