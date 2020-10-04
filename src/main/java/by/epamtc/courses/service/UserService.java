package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;

import java.util.Locale;
import java.util.Map;

public interface UserService {

    Map<String, String> validateUserAuthData(String login, String password);

    Map<String, String> validateUserAuthData(String login, String password, Locale lang);

    User authenticate(String login, String password) throws ServiceException;

    void register(UserAuthData user) throws ServiceException;
}
