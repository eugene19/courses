package by.epamtc.courses.service;

import by.epamtc.courses.entity.CourseResult;

import java.util.Locale;
import java.util.Map;

/**
 * Interface of course result service layer
 *
 * @author DEA
 */
public interface CourseResultService {

    /**
     * Check if all student have results (marks) at define course
     *
     * @param courseId id of course to check results
     * @return true if all student have results, else - false
     * @throws ServiceException if an service exception occurred while processing
     */
    boolean areAllStudentsHaveResult(int courseId) throws ServiceException;

    /**
     * Set student's course result
     *
     * @param studentId id of student whom set result
     * @param courseId  id of course to set result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws ServiceException if an service exception occurred while processing
     */
    void setCourseResult(int studentId, int courseId, int mark, String comment) throws ServiceException;

    /**
     * Take course result for some student
     *
     * @param studentId id of student whom take result
     * @param courseId  id of course to take result
     * @return student's result at course
     * @throws ServiceException if an service exception occurred while processing
     */
    CourseResult takeCourseResultForUser(int studentId, int courseId) throws ServiceException;

    /**
     * Check if course result is valid
     *
     * @param parameters request's parameters with values from client
     * @param locale     locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale);
}
