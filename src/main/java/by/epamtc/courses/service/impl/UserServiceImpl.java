package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseDao;
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
    public Map<User, UserCourseStatus> takeUsersOnCourse(int courseId) throws ServiceException {
        try {
            CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
            Course course = courseDao.findCourseById(courseId);
            CourseStatus status = course.getStatus();

            if (status == CourseStatus.NOT_STARTED) {
                return userDao.findAllStudentsOnCourse(courseId);
            }

            return userDao.findStudentsOnCourseWithStatus(courseId, UserCourseStatus.ENTERED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException {
        if (status == UserCourseStatus.ENTERED) {
            CourseService courseService = ServiceProvider.getInstance().getCourseService();
            Course course = courseService.getCourse(courseId);
            int studentsLimit = course.getStudentsLimit();

            Map<User, UserCourseStatus> usersOnCourse = takeUsersOnCourse(courseId);
            int enteredUsers = countEnteredUsers(usersOnCourse);

            if (enteredUsers >= studentsLimit) {
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
    public User getUserById(int userId) throws ServiceException {
        try {
            return userDao.findUserById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countEnteredUsersOnCourse(int courseId) throws ServiceException {
        try {
            Map<User, UserCourseStatus> enteredUserOnCourse = userDao.findStudentsOnCourseWithStatus(courseId, UserCourseStatus.ENTERED);
            return enteredUserOnCourse.size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private int countEnteredUsers(Map<User, UserCourseStatus> usersOnCourse) {
        int count = 0;

        for (Map.Entry<User, UserCourseStatus> entrySet : usersOnCourse.entrySet()) {
            UserCourseStatus status = entrySet.getValue();
            if (status == UserCourseStatus.ENTERED) {
                count++;
            }
        }

        return count;
    }
}
