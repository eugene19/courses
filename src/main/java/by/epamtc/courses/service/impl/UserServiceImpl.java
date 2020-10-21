package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.*;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.validation.UserValidator;

import java.util.Locale;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public User authenticate(String login, String password) throws ServiceException {
        try {
            return userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void register(UserAuthData user) throws ServiceException {
        try {
            userDao.register(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> validateUserAuthData(Map<String, String[]> parameters, Locale lang) {
        UserValidator validator = new UserValidator(parameters, lang);

        return validator
                .validateLogin()
                .validatePassword()
                .getErrors();
    }

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

    @Override
    public void update(User user) throws ServiceException {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<User, UserCourseStatus> findUsersOnCourse(int courseId) throws ServiceException {
        try {
            CourseService courseService = ServiceProvider.getInstance().getCourseService();
            Course course = courseService.findCourseById(courseId);
            CourseStatus status = course.getStatus();

            return (status == CourseStatus.NOT_STARTED) ?
                    userDao.findAllStudentsOnCourse(courseId) :
                    userDao.findStudentsOnCourseWithStatus(courseId, UserCourseStatus.ENTERED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException {
        if (status == UserCourseStatus.ENTERED) {
            CourseService courseService = ServiceProvider.getInstance().getCourseService();
            Course course = courseService.findCourseById(courseId);
            int enteredUsers = countStudentsOnCourseInStatus(courseId, UserCourseStatus.ENTERED);

            if (enteredUsers >= course.getStudentsLimit()) {
                return false;
            }
        }

        try {
            userDao.updateUserCourseStatus(userId, courseId, status);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserById(int userId) throws ServiceException {
        try {
            return userDao.findUserById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
