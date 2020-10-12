package by.epamtc.courses.dao;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;

public interface CourseDao {

    List<Course> takeAllCourses() throws DaoException;

    void createNew(Course course) throws DaoException;

    Course getCourse(int courseId) throws DaoException;

    void update(Course course) throws DaoException;

    void enterUserOnCourse(int userId, int courseId) throws DaoException;

    void leaveUserFromCourse(int userId, int courseId) throws DaoException;

    UserCourseStatus getUserCourseStatus(int userId, int courseId) throws DaoException;
}
