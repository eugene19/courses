package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserData;

public interface UserService {

    User authenticate(String login, String password) throws ServiceException;

    void register(UserData user) throws ServiceException;
}
