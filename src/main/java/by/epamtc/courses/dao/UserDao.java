package by.epamtc.courses.dao;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.Map;

public interface UserDao {

    User authenticate(String login, String password) throws DaoException;

    void register(UserAuthData user) throws DaoException;

    boolean update(User user) throws DaoException;

    Map<User, UserCourseStatus> takeUsersOnCourse(int courseId) throws DaoException;

    Map<User, UserCourseStatus> getEnteredUserOnCourse(int courseId) throws DaoException;

    boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws DaoException;

    User getUserById(int userId) throws DaoException;
}
