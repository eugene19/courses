package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.*;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.validation.UserValidator;

import java.util.Locale;
import java.util.Map;

/**
 * Implementation of user service layer
 *
 * @author DEA
 */
public class UserServiceImpl implements UserService {

    /**
     * Instance of course dao
     */
    private CourseDao courseDao = DaoProvider.getInstance().getCourseDao();

    /**
     * Instance of user dao
     */
    private UserDao userDao = DaoProvider.getInstance().getUserDao();

    /**
     * Authentication of user if such user exist.
     * If user is not found return null
     *
     * @param login    entered login value
     * @param password entered password value
     * @return <code>User</code> object of user
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public User authenticate(String login, String password) throws ServiceException {
        try {
            return userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Calculate count of student on course with some status
     *
     * @param courseId id of course to find
     * @param status   value of status
     * @return integer count of students
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public int countStudentsOnCourseInStatus(int courseId, UserCourseStatus status) throws ServiceException {
        try {
            return userDao
                    .findStudentsOnCourseWithStatus(courseId, status)
                    .size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find user by id. If user is not found return null
     *
     * @param userId id of user to find
     * @return <code>User</code> object of user
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public User findUserById(int userId) throws ServiceException {
        try {
            return userDao.findUserById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find all students on course if course not started.
     * If course in progress find only entered students
     *
     * @param courseId id of course to find
     * @return list of students with statuses as <code>Map</code>
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public Map<User, UserCourseStatus> findAllStudentsOnCourse(int courseId) throws ServiceException {
        try {
            Course course = courseDao.findCourseById(courseId);
            CourseStatus status = course.getStatus();

            return (status == CourseStatus.NOT_STARTED) ?
                    userDao.findAllStudentsOnCourse(courseId) :
                    userDao.findStudentsOnCourseWithStatus(courseId, UserCourseStatus.ENTERED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Register (add) new user
     *
     * @param user user's data
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void register(UserAuthData user) throws ServiceException {
        try {
            userDao.register(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Check if user auth data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    @Override
    public Map<String, String> validateUserAuthData(Map<String, String[]> parameters, Locale lang) {
        UserValidator validator = new UserValidator(parameters, lang);

        return validator
                .validateLogin()
                .validatePassword()
                .getErrors();
    }

    /**
     * Check if user profile data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    @Override
    public Map<String, String> validateUserProfileData(Map<String, String[]> parameters, Locale lang) {
        UserValidator validator = new UserValidator(parameters, lang);

        return validator
                .validateSurname()
                .validateName()
                .validateEmail()
                .validateBirthday()
                .validateRole()
                .getErrors();
    }

    /**
     * Check if user registration data is valid
     *
     * @param parameters request's parameters with values from client
     * @param lang       locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    @Override
    public Map<String, String> validateUserRegistrationData(Map<String, String[]> parameters, Locale lang) {
        UserValidator validator = new UserValidator(parameters, lang);

        return validator
                .validateLogin()
                .validatePassword()
                .validateSurname()
                .validateName()
                .validateEmail()
                .validateBirthday()
                .validateRole()
                .getErrors();
    }

    /**
     * Update user's data
     *
     * @param user object <code>User</code> of client
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void update(User user) throws ServiceException {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update student status on course
     *
     * @param userId   id of student to update
     * @param courseId id of course to update
     * @param status   new student's status
     * @return true if updating is success, else - false
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException {
        try {
            if (status == UserCourseStatus.ENTERED) {
                Course course = courseDao.findCourseById(courseId);
                int enteredUsers = countStudentsOnCourseInStatus(courseId, UserCourseStatus.ENTERED);

                if (enteredUsers >= course.getStudentsLimit()) {
                    return false;
                }
            }

            userDao.updateUserCourseStatus(userId, courseId, status);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
