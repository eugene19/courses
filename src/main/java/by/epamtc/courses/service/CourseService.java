package by.epamtc.courses.service;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CourseService {

    List<Course> takeAllCourses() throws ServiceException;

    Map<Course, CourseResult> takeCoursesWithResultForStudent(int userId) throws ServiceException;

    List<Course> takeCoursesWithStatus(CourseStatus status) throws ServiceException;

    Map<String, String> validateCourse(Map<String, String[]> parameters, Locale locale);

    void createNew(Course course) throws ServiceException;

    Course getCourse(int courseId) throws ServiceException;

    void update(Course course) throws ServiceException;

    void enterUserOnCourse(int userId, int courseId) throws ServiceException;

    void leaveUserFromCourse(int userId, int courseId) throws ServiceException;

    UserCourseStatus getUserCourseStatus(int userId, int courseId) throws ServiceException;

    void updateStatus(int courseId, CourseStatus courseStatus) throws ServiceException;

    void updateCourseMaterialPath(int courseId, String fileName) throws ServiceException;
}
