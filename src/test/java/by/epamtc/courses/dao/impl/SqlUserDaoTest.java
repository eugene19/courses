package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.User;
import org.junit.Assert;
import org.junit.Test;

public class SqlUserDaoTest {

    private static final String VALID_LOGIN = "user1";
    private static final String NOT_VALID_LOGIN = "no";
    private static final String VALID_PASSWORD = "698d51a19d8a121ce581499d7b701668";
    private static final String NOT_VALID_PASSWORD = "no";

    private final UserDao userDao = new SqlUserDao();

    @Test
    public void authorizeValidUser() throws DaoException {
        User authenticate = userDao.authenticate(VALID_LOGIN, VALID_PASSWORD);
        Assert.assertNotNull(authenticate);
    }

    @Test
    public void authorizeNonExistingUser() throws DaoException {
        User authenticate = userDao.authenticate(NOT_VALID_LOGIN, NOT_VALID_PASSWORD);
        Assert.assertNull(authenticate);
    }
}