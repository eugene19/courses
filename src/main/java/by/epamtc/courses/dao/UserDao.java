package by.epamtc.courses.dao;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.Map;

/**
 * Interface of user dao layer
 *
 * @author DEA
 */
public interface UserDao {

    /**
     * Authentication of user if such user exist.
     * If user is not found return null
     *
     * @param login    entered login value
     * @param password entered password value
     * @return <code>User</code> object of user
     * @throws DaoException if an dao exception occurred while processing
     */
    User authenticate(String login, String password) throws DaoException;

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId id of course to find
     * @return list of students with statuses as <code>Map</code>
     * @throws DaoException if an dao exception occurred while processing
     */
    Map<User, UserCourseStatus> findAllStudentsOnCourse(int courseId) throws DaoException;

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId   id of course to find
     * @param userStatus value of status to find
     * @return list of students with statuses as <code>Map</code>
     * @throws DaoException if an dao exception occurred while processing
     */
    Map<User, UserCourseStatus> findStudentsOnCourseWithStatus(int courseId, UserCourseStatus userStatus) throws DaoException;

    /**
     * Find user by id. If user is not found return null
     *
     * @param userId id of user to find
     * @return <code>User</code> object of user
     * @throws DaoException if an dao exception occurred while processing
     */
    User findUserById(int userId) throws DaoException;

    /**
     * Register (add) new user
     *
     * @param user user's data
     * @throws DaoException if an dao exception occurred while processing
     */
    void register(UserAuthData user) throws DaoException;

    /**
     * Update user's data
     *
     * @param user object <code>User</code> of client
     * @throws DaoException if an dao exception occurred while processing
     */
    void update(User user) throws DaoException;

    /**
     * Update student status on course
     *
     * @param userId   id of student to update
     * @param courseId id of course to update
     * @param status   new student's status
     * @throws DaoException if an dao exception occurred while processing
     */
    void updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws DaoException;
}
