package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolException;
import by.epamtc.courses.dao.impl.connection.ConnectionPoolFactory;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private static final String GET_ALL = "SELECT courses.id, summary, description, " +
            "students_limit, start_date, end_date, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id;";

    private static final String GET_ALL_FOR_LECTURER = "SELECT courses.id, summary, description, " +
            "students_limit, start_date, end_date, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id " +
            "WHERE lecturer_id = ?;";

    private static final String GET_BY_ID = "SELECT courses.id, summary, description, " +
            "students_limit, start_date, end_date, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id " +
            "WHERE courses.id = ?;";

    private static final String INSERT_COURSE = "INSERT INTO courses " +
            "(summary, description, students_limit) VALUES (?, ?, ?);";

    private static final String INSERT_COURSE_RUN = "INSERT INTO course_runs " +
            "(course_id, start_date, end_date, lecturer_id, status_id) " +
            "VALUES (?, ?, ?, ?, ?);";

    private static final String UPDATE_COURSE = "UPDATE courses " +
            "SET summary = ?, description = ?, students_limit = ? WHERE id = ?;";

    private static final String UPDATE_COURSE_RUN = "UPDATE course_runs " +
            "SET start_date = ?, end_date = ?, lecturer_id = ?, status_id = ? " +
            "WHERE id = ?;";

    private static final String DELETE_COURSE = "DELETE FROM courses WHERE id = ?;";

    private static final String SUBSCRIBE_USER = "INSERT INTO user_courses " +
            "(user_id, course_id) " +
            "VALUES (?, ?);";

    private static final String GET_ALL_FOR_STUDENT = "SELECT courses.id, summary, description, " +
            "students_limit, start_date, end_date, lecturer_id, status " +
            "FROM courses " +
            "LEFT JOIN course_runs ON courses.id = course_runs.course_id " +
            "LEFT JOIN course_statuses ON course_runs.status_id = course_statuses.id " +
            "LEFT JOIN user_courses ON courses.id = user_courses.course_id " +
            "WHERE user_id = ?;";

    @Override
    public int insert(Course ob) throws DaoException {
        Connection connection = null;

        try {
            connection = ConnectionPoolFactory.getInstance().getConnectionPool().takeConnection();
            connection.setAutoCommit(false);

            PreparedStatement courseStatement = connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            courseStatement.setString(1, ob.getSummary());
            courseStatement.setString(2, ob.getDescription());
            courseStatement.setInt(3, ob.getStudentsLimit());

            courseStatement.executeUpdate();

            ResultSet resultSet = courseStatement.getGeneratedKeys();
            resultSet.next();
            int courseId = resultSet.getInt(1);

            PreparedStatement courseRunStatement = connection.prepareStatement(INSERT_COURSE_RUN);

            courseRunStatement.setInt(1, courseId);
            courseRunStatement.setDate(2, Date.valueOf(ob.getStartDate()));
            courseRunStatement.setDate(3, Date.valueOf(ob.getEndDate()));
            courseRunStatement.setInt(4, ob.getLecturerId());
            courseRunStatement.setInt(5, ob.getStatus().getId());

            courseRunStatement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            //log
            e.printStackTrace();
            rollback(connection);
            throw new DaoException("Error while insert course ", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    //log
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    private Connection takeConnection() throws DaoException {
        try {
            return ConnectionPoolFactory.getInstance().getConnectionPool().takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error while getting connection to DB.", e);
        }
    }

    private void rollback(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException("Error while insert course ", e);
            }
        }
    }

    @Override
    public void update(Course ob) throws DaoException {
        Connection connection = null;

        try {
            connection = takeConnection();
            connection.setAutoCommit(false);

            PreparedStatement courseStatement = connection.prepareStatement(UPDATE_COURSE);
            courseStatement.setString(1, ob.getSummary());
            courseStatement.setString(2, ob.getDescription());
            courseStatement.setInt(3, ob.getStudentsLimit());

            int courseId = courseStatement.executeQuery().getInt(1);

            PreparedStatement courseRunStatement = connection.prepareStatement(UPDATE_COURSE_RUN);

            courseRunStatement.setDate(1, Date.valueOf(ob.getStartDate()));
            courseRunStatement.setDate(2, Date.valueOf(ob.getEndDate()));
            courseRunStatement.setInt(3, ob.getLecturerId());
            courseRunStatement.setInt(4, ob.getStatus().getId());
            courseRunStatement.setInt(5, courseId);

            courseStatement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            //log
            rollback(connection);
            throw new DaoException("Error while update course ", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    //log
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection connection = takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while delete course", e);
        }
    }

    @Override
    public Course getById(int id) throws DaoException {
        Course course = new Course();

        try (Connection connection = takeConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = createCourse(resultSet);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get course by id", e);
        }

        return course;
    }

    @Override
    public List<Course> getAll() throws DaoException {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = takeConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while (resultSet.next()) {
                Course course = createCourse(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get all courses", e);
        }

        return courses;
    }

    private Course createCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();

        course.setId(resultSet.getInt(1));
        course.setSummary(resultSet.getString(2));
        course.setDescription(resultSet.getString(3));
        course.setStudentsLimit(resultSet.getInt(4));
        course.setStartDate(resultSet.getDate(5).toLocalDate());
        course.setEndDate(resultSet.getDate(6).toLocalDate());
        course.setLecturerId(resultSet.getInt(7));
        course.setStatus(CourseStatus.valueOf(resultSet.getString(8)));

        return course;
    }

    public List<Course> getAllForLecturer(int userId) throws DaoException {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = takeConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_FOR_LECTURER);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = createCourse(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get all courses", e);
        }

        return courses;
    }

    public List<Course> getAllForStudent(int id) throws DaoException {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = takeConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_FOR_STUDENT);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = createCourse(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while get all courses", e);
        }

        return courses;
    }

    public void subscribeUserOnCourse(int userId, int courseId) throws DaoException {
        try (Connection connection = takeConnection()) {
            PreparedStatement statement = connection.prepareStatement(SUBSCRIBE_USER);
            statement.setInt(1, userId);
            statement.setInt(2, courseId);

            statement.execute();
        } catch (SQLException e) {
            //log
            throw new DaoException("Error while subscribe user on course", e);
        }
    }


}
