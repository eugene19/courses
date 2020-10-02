package by.epamtc.courses.dao;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;

public interface UserDao {

    User authenticate(String login, String password) throws DaoException;

    boolean register(UserAuthData user) throws DaoException;
}
