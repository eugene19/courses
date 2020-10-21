package by.epamtc.courses.service;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CourseService {

    void create(Course course) throws ServiceException;

    void enterStudentOnCourse(int userId, int courseId) throws ServiceException;

    List<Course> findAllCourses() throws ServiceException;

    Course findCourseById(int courseId) throws ServiceException;

    Map<Course, CourseResult> findCoursesWithResultForStudent(int studentId) throws ServiceException;

    List<Course> findCoursesWithStatus(CourseStatus status) throws ServiceException;

    void leaveStudentFromCourse(int userId, int courseId) throws ServiceException;

    UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws ServiceException;

    void update(Course course) throws ServiceException;

    void updateStatus(int courseId, CourseStatus courseStatus) throws ServiceException;

    void updateMaterialPath(int courseId, String fileName) throws ServiceException;

    Map<String, String> validateCourse(Map<String, String[]> parameters, Locale locale);
}
