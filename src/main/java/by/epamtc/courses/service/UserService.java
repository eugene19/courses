package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;

import java.util.Locale;
import java.util.Map;

public interface UserService {

    User authenticate(String login, String password) throws ServiceException;

    void register(UserAuthData user) throws ServiceException;

    Map<String, String> validateUserAuthData(Map<String, String[]> parameters, Locale lang);

    Map<String, String> validateUserRegistrationData(Map<String, String[]> parameters, Locale lang);
}
