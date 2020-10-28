package by.epamtc.courses.dao;

import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;

import java.util.List;

/**
 * Interface of course dao layer
 *
 * @author DEA
 */
public interface CourseDao {

    /**
     * Add student's application on course
     *
     * @param studentId id of student who apply
     * @param courseId  id of course to apply
     * @throws DaoException if an dao exception occurred while processing
     */
    void addStudentApplicationOnCourse(int studentId, int courseId) throws DaoException;

    /**
     * Count number of courses in status
     *
     * @param statuses value of status to count
     * @return number of courses with statuses
     * @throws DaoException if an dao exception occurred while processing
     */
    int countCoursesInStatus(String statuses) throws DaoException;

    /**
     * Create (add) new course
     *
     * @param course entity of <code>Course</code> object
     * @throws DaoException if an dao exception occurred while processing
     */
    void create(Course course) throws DaoException;

    /**
     * Find list of courses with their results as <code>Map</code>
     * for student
     *
     * @param studentId id of student to find
     * @return <code>Map</code> of courses with results for student
     * @throws DaoException if an dao exception occurred while processing
     */
    List<Course> findAllCoursesWithResultsForStudent(int studentId) throws DaoException;

    /**
     * Find course by identifier
     *
     * @param courseId id of course to find
     * @return object of <code>Course</code> entity or null if such course does not exist
     * @throws DaoException if an dao exception occurred while processing
     */
    Course findCourseById(int courseId) throws DaoException;

    /**
     * Find list of courses of define status
     *
     * @param statuses value of status to find
     * @param count    limit of courses
     * @param offset   offset of courses
     * @param sort     name of column to sort list
     * @return List of courses with define status
     * @throws DaoException if an dao exception occurred while processing
     */
    List<Course> findCoursesWithStatusForPage(String statuses, int count,
                                              int offset, String sort) throws DaoException;

    /**
     * Left student from course
     *
     * @param studentId id of student to leave
     * @param courseId  id of course from which should leave
     * @throws DaoException if an dao exception occurred while processing
     */
    void leaveStudentFromCourse(int studentId, int courseId) throws DaoException;

    /**
     * Take students status
     *
     * @param userId   id of student to take status
     * @param courseId id of course to take status
     * @return <code>UserCourseStatus</code> object which contain student's status
     * @throws DaoException if an dao exception occurred while processing
     */
    UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws DaoException;

    /**
     * Update course's data
     *
     * @param course object <code>Course</code> of course to update
     * @throws DaoException if an dao exception occurred while processing
     */
    void update(Course course) throws DaoException;

    /**
     * Update course's material path
     *
     * @param courseId id of course to update
     * @param fileName new value of file name
     * @throws DaoException if an dao exception occurred while processing
     */
    void updateMaterialPath(int courseId, String fileName) throws DaoException;

    /**
     * Update course's status
     *
     * @param courseId     id of course to update
     * @param courseStatus new value of course status
     * @throws DaoException if an dao exception occurred while processing
     */
    void updateStatus(int courseId, CourseStatus courseStatus) throws DaoException;
}
