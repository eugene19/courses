package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.validation.UserValidator;

import java.util.HashMap;
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
    public Map<String, String> validateUserAuthData(String login, String password, Locale lang) {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put(ParameterName.LOGIN, new String[]{login});
        parameterMap.put(ParameterName.PASSWORD, new String[]{password});

        UserValidator validator = new UserValidator(parameterMap, lang);

        return validator.validateLogin().validatePassword().getErrors();
    }

    public Map<String, String> validateUserAuthDataNew(Map<String, String[]> parameters, Locale lang) {
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
}
