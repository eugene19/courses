package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;

public interface UserService {

    User authenticate(String login, String password) throws ServiceException;

    void register(UserAuthData user) throws ServiceException;
}
