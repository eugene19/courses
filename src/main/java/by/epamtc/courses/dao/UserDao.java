package by.epamtc.courses.dao;

import by.epamtc.courses.entity.User;

public interface UserDao extends InterfaceDao<User> {
    User getByLogin(String login) throws DaoException;
}
