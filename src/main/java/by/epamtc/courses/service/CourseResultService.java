package by.epamtc.courses.service;

import by.epamtc.courses.entity.CourseResult;

import java.util.Locale;
import java.util.Map;

public interface CourseResultService {

    boolean areAllStudentsHaveResult(int courseId) throws ServiceException;

    void setCourseResult(int studentId, int courseId, int mark, String comment) throws ServiceException;

    CourseResult takeCourseResultForUser(int studentId, int courseId) throws ServiceException;

    Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale);
}
