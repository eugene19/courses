package by.epamtc.courses.dao.impl;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.connection.ConnectionPool;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.entity.UserRole;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.*;

public class SqlUserDaoTest {

    private final UserDao userDao = new SqlUserDao();

    @Test(timeout = 100)
    public void authorizeValidUser() throws DaoException {
        String validLogin = "user1";
        String validPassword = "698d51a19d8a121ce581499d7b701668";
        User authenticate = userDao.authenticate(validLogin, validPassword);

        assertNotNull("There is no valid user", authenticate);
    }

    @Test(timeout = 100)
    public void authorizeNotExistingUser() throws DaoException {
        String notValidLogin = "no";
        String notValidPassword = "no";
        User authenticate = userDao.authenticate(notValidLogin, notValidPassword);

        assertNull("There is not existing user", authenticate);
    }

    @Test(timeout = 100)
    public void findStudentsOnCourse() throws DaoException {
        int courseId = 4;
        Map<User, UserCourseStatus> students = userDao.findAllStudentsOnCourse(courseId);

        assertEquals("Wrong count of students", 8, students.size());
    }

    @Test(timeout = 100)
    public void findStudentsOnNotExistingCourse() throws DaoException {
        int notExistingCourseId = -1;
        Map<User, UserCourseStatus> students = userDao.findAllStudentsOnCourse(notExistingCourseId);

        assertEquals("Wrong count of students", 0, students.size());
    }

    @Test(timeout = 100)
    public void findValidUserById() throws DaoException {
        int userId = 1;
        User user = userDao.findUserById(userId);

        assertNotNull("There is no valid user", user);
    }

    @Test(timeout = 100)
    public void findNotExistingUserById() throws DaoException {
        int userId = -1;
        User user = userDao.findUserById(userId);

        assertNull("There is not existing user", user);
    }

    @Test(timeout = 100)
    public void registerValidUser() throws DaoException, SQLException {
        UserAuthData userData = takeValidUserAuthData();

        userDao.register(userData);

        boolean hasUser = findUserFromDB(userData.getLogin(), userData.getPassword());
        assertTrue("User is not been registered", hasUser);
    }

    private boolean findUserFromDB(String login, String password) throws SQLException {
        String sqlFindUserByLoginPassword = "select * from users where login = ? and password = ?;";

        Connection connection = ConnectionPool.getInstance().takeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlFindUserByLoginPassword);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    private UserAuthData takeValidUserAuthData() {
        UserAuthData userData = new UserAuthData();

        userData.setLogin("testUserLogin");
        userData.setPassword("testUserPassword");
        userData.setSurname("testUserSurname");
        userData.setName("testUserName");
        userData.setEmail("testUserEmail");
        userData.setBirthday(LocalDate.now());
        userData.setRole(UserRole.STUDENT);
        userData.setPhotoPath(null);

        return userData;
    }
}