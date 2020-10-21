package by.epamtc.courses.service;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.Locale;
import java.util.Map;

public interface UserService {

    User authenticate(String login, String password) throws ServiceException;

    int countStudentsOnCourseInStatus(int courseId, UserCourseStatus status) throws ServiceException;

    User findUserById(int userId) throws ServiceException;

    void register(UserAuthData user) throws ServiceException;

    Map<User, UserCourseStatus> findUsersOnCourse(int courseId) throws ServiceException;

    Map<String, String> validateUserAuthData(Map<String, String[]> parameters, Locale lang);

    Map<String, String> validateUserProfileData(Map<String, String[]> parameters, Locale lang);

    Map<String, String> validateUserRegistrationData(Map<String, String[]> parameters, Locale lang);

    void update(User user) throws ServiceException;

    boolean updateUserCourseStatus(int userId, int courseId, UserCourseStatus status) throws ServiceException;
}
