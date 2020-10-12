package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.ServiceException;
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
    public Map<User, UserCourseStatus> getUserOnCourse(int courseId) throws ServiceException {
        try {
            return userDao.getUserOnCourse(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException {
        try {
            userDao.updateUserCourseStatus(userId, courseId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
