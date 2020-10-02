package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserData;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.UserService;

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
    public void register(UserData user) throws ServiceException {
        try {
            userDao.register(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
