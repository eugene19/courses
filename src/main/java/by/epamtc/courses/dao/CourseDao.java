package by.epamtc.courses.dao;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;

public interface CourseDao {

    void addStudentApplicationOnCourse(int userId, int courseId) throws DaoException;

    void create(Course course) throws DaoException;

    List<Course> findAllCourses() throws DaoException;

    List<Course> findCoursesWithStatus(CourseStatus status) throws DaoException;

    Course findCourseById(int courseId) throws DaoException;

    List<Course> findAllCoursesWithResultsForStudent(int userId) throws DaoException;

    void leaveStudentFromCourse(int userId, int courseId) throws DaoException;

    UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws DaoException;

    void update(Course course) throws DaoException;

    void updateCourseMaterialPath(int courseId, String fileName) throws DaoException;

    void updateStatus(int courseId, CourseStatus courseStatus) throws DaoException;
}
