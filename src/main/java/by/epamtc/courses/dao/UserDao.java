package by.epamtc.courses.dao;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.Map;

public interface UserDao {

    User authenticate(String login, String password) throws DaoException;

    Map<User, UserCourseStatus> findAllStudentsOnCourse(int courseId) throws DaoException;

    Map<User, UserCourseStatus> findStudentsOnCourseWithStatus(int courseId, UserCourseStatus userStatus) throws DaoException;

    User findUserById(int userId) throws DaoException;

    void register(UserAuthData user) throws DaoException;

    void update(User user) throws DaoException;

    void updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws DaoException;
}
