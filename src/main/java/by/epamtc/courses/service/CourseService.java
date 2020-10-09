package by.epamtc.courses.service;

import by.epamtc.courses.entity.Course;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CourseService {

    List<Course> takeAllCourses() throws ServiceException;

    Map<String, String> validateCourse(Map<String, String[]> parameters, Locale locale);

    void createNew(Course course) throws ServiceException;

    Course getCourse(int courseId) throws ServiceException;
}
