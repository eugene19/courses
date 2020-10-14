package by.epamtc.courses.service;

import by.epamtc.courses.entity.CourseResult;

import java.util.Locale;
import java.util.Map;

public interface CourseResultService {

    CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws ServiceException;

    Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale);

    void setCourseResult(int studentId, int courseId, int mark, String comment) throws ServiceException;

    boolean checkAllStudentHasResult(int courseId) throws ServiceException;
}
