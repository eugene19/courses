package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.Locale;
import java.util.Map;

/**
 * Interface of user service layer
 *
 * @author DEA
 */
public interface UserService {

    /**
     * Authentication of user if such user exist.
     * If user is not found return null
     *
     * @param login    entered login value
     * @param password entered password value
     * @return <code>User</code> object of user
     * @throws ServiceException if an service exception occurred while processing
     */
    User authenticate(String login, String password) throws ServiceException;

    /**
     * Calculate count of student on course with some status
     *
     * @param courseId id of course to find
     * @param status   value of status
     * @return integer count of students
     * @throws ServiceException if an service exception occurred while processing
     */
    int countStudentsOnCourseInStatus(int courseId, UserCourseStatus status) throws ServiceException;

    /**
     * Find user by id. If user is not found return null
     *
     * @param userId id of user to find
     * @return <code>User</code> object of user
     * @throws ServiceException if an service exception occurred while processing
     */
    User findUserById(int userId) throws ServiceException;

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId id of course to find
     * @return list of students with statuses as <code>Map</code>
     * @throws ServiceException if an service exception occurred while processing
     */
    Map<User, UserCourseStatus> findAllStudentsOnCourse(int courseId) throws ServiceException;

    /**
     * Register (add) new user
     *
     * @param user user's data
     * @throws ServiceException if an service exception occurred while processing
     */
    void register(UserAuthData user) throws ServiceException;

    /**
     * Check if user auth data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    Map<String, String> validateUserAuthData(Map<String, String[]> parameters, Locale lang);

    /**
     * Check if user profile data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    Map<String, String> validateUserProfileData(Map<String, String[]> parameters, Locale lang);

    /**
     * Check if user registration data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    Map<String, String> validateUserRegistrationData(Map<String, String[]> parameters, Locale lang);

    /**
     * Update user's data
     *
     * @param user object <code>User</code> of client
     * @throws ServiceException if an service exception occurred while processing
     */
    void update(User user) throws ServiceException;

    /**
     * Update student status on course
     *
     * @param userId   id of student to update
     * @param courseId id of course to update
     * @param status   new student's status
     * @return true if updating is success, else - false
     * @throws ServiceException if an service exception occurred while processing
     */
    boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException;
}
