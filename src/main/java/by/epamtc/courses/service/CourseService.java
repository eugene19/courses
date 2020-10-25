package by.epamtc.courses.service;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface of course service layer
 *
 * @author DEA
 */
public interface CourseService {

    /**
     * Create (add) new course
     *
     * @param course entity of <code>Course</code> object
     * @throws ServiceException if an service exception occurred while processing
     */
    void create(Course course) throws ServiceException;

    /**
     * Enter student at the course
     *
     * @param studentId id of student to enter
     * @param courseId  id of course to enter
     * @throws ServiceException if an service exception occurred while processing
     */
    void enterStudentOnCourse(int studentId, int courseId) throws ServiceException;

    /**
     * Find all existing courses
     *
     * @return <code>List</code> of courses
     * @throws ServiceException if an service exception occurred while processing
     */
    List<Course> findAllCourses() throws ServiceException;

    /**
     * Find course by identifier
     *
     * @param courseId id of course to find
     * @return object of <code>Course</code> entity or null if such course does not exist
     * @throws ServiceException if an service exception occurred while processing
     */
    Course findCourseById(int courseId) throws ServiceException;

    /**
     * Find list of courses with their results as <code>Map</code>
     * for student
     *
     * @param studentId id of student to find
     * @return <code>Map</code> of courses with results for student
     * @throws ServiceException if an service exception occurred while processing
     */
    Map<Course, CourseResult> findCoursesWithResultForStudent(int studentId) throws ServiceException;

    /**
     * Find list of courses of define statuses
     *
     * @param statuses values of status to find
     * @return List of courses with define statuses
     * @throws ServiceException if an service exception occurred while processing
     */
    List<Course> findCoursesWithStatus(String[] statuses) throws ServiceException;

    /**
     * Left student from course
     *
     * @param studentId id of student to leave
     * @param courseId  id of course from which should leave
     * @throws ServiceException if an service exception occurred while processing
     */
    void leaveStudentFromCourse(int studentId, int courseId) throws ServiceException;

    /**
     * Take students status
     *
     * @param userId   id of student to take status
     * @param courseId id of course to take status
     * @return <code>UserCourseStatus</code> object which contain student's status
     * @throws ServiceException if an service exception occurred while processing
     */
    UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws ServiceException;

    /**
     * Check if course's date is valid
     *
     * @param parameters request's parameters with values from client
     * @param locale     locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    Map<String, String> validateCourse(Map<String, String[]> parameters, Locale locale);

    /**
     * Update course's data
     *
     * @param course object <code>Course</code> of course to update
     * @throws ServiceException if an service exception occurred while processing
     */
    void update(Course course) throws ServiceException;

    /**
     * Update course's material path
     *
     * @param courseId id of course to update
     * @param fileName new value of file name
     * @throws ServiceException if an service exception occurred while processing
     */
    void updateMaterialPath(int courseId, String fileName) throws ServiceException;

    /**
     * Update course's status
     *
     * @param courseId     id of course to update
     * @param courseStatus new value of course status
     * @throws ServiceException if an service exception occurred while processing
     */
    void updateStatus(int courseId, CourseStatus courseStatus) throws ServiceException;
}
