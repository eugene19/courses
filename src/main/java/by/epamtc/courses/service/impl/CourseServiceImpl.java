package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.validation.CourseValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of course service layer
 *
 * @author DEA
 */
public class CourseServiceImpl implements CourseService {

    /**
     * Instance of course dao
     */
    private CourseDao courseDao = DaoProvider.getInstance().getCourseDao();

    /**
     * Instance of course's result dao
     */
    private CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();

    /**
     * Create (add) new course
     *
     * @param course entity of <code>Course</code> object
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void create(Course course) throws ServiceException {
        try {
            courseDao.create(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Enter student at the course
     *
     * @param studentId id of student to enter
     * @param courseId  id of course to enter
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void enterStudentOnCourse(int studentId, int courseId) throws ServiceException {
        try {
            UserCourseStatus userCourseStatus = courseDao.takeUserCourseStatus(studentId, courseId);
            if (userCourseStatus == null) {
                courseDao.addStudentApplicationOnCourse(studentId, courseId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find all existing courses
     *
     * @return <code>List</code> of courses
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public List<Course> findAllCourses() throws ServiceException {
        try {
            return courseDao.findAllCourses();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find course by identifier
     *
     * @param courseId id of course to find
     * @return object of <code>Course</code> entity or null if such course does not exist
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public Course findCourseById(int courseId) throws ServiceException {
        try {
            return courseDao.findCourseById(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find list of courses with their results as <code>Map</code>
     * for student
     *
     * @param studentId id of student to find
     * @return <code>Map</code> of courses with results for student
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public Map<Course, CourseResult> findCoursesWithResultForStudent(int studentId) throws ServiceException {
        Map<Course, CourseResult> coursesWithResults = new HashMap<>();

        try {
            List<Course> courses = courseDao.findAllCoursesWithResultsForStudent(studentId);

            for (Course course : courses) {
                CourseResult courseResult = courseResultDao.takeCourseResult(studentId, course.getId());
                coursesWithResults.put(course, courseResult);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return coursesWithResults;
    }

    /**
     * Find list of courses of define status
     *
     * @param status value of status to find
     * @return List of courses with define status
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public List<Course> findCoursesWithStatus(CourseStatus status) throws ServiceException {
        try {
            return courseDao.findCoursesWithStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Left student from course
     *
     * @param studentId id of student to leave
     * @param courseId  id of course from which should leave
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void leaveStudentFromCourse(int studentId, int courseId) throws ServiceException {
        try {
            courseDao.leaveStudentFromCourse(studentId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Take students status
     *
     * @param userId   id of student to take status
     * @param courseId id of course to take status
     * @return <code>UserCourseStatus</code> object which contain student's status
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws ServiceException {
        try {
            return courseDao.takeUserCourseStatus(userId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Check if course's date is valid
     *
     * @param parameters request's parameters with values from client
     * @param locale     locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    @Override
    public Map<String, String> validateCourse(Map<String, String[]> parameters, Locale locale) {
        CourseValidator validator = new CourseValidator(parameters, locale);

        return validator
                .validateSummary()
                .validateDescription()
                .validateStartDate()
                .validateEndDate()
                .validateStudentsLimit()
                .getErrors();
    }

    /**
     * Update course's data
     *
     * @param course object <code>Course</code> of course to update
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void update(Course course) throws ServiceException {
        try {
            courseDao.update(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update course's status
     *
     * @param courseId     id of course to update
     * @param courseStatus new value of course status
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void updateStatus(int courseId, CourseStatus courseStatus) throws ServiceException {
        try {
            courseDao.updateStatus(courseId, courseStatus);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update course's material path
     *
     * @param courseId id of course to update
     * @param fileName new value of file name
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void updateMaterialPath(int courseId, String fileName) throws ServiceException {
        try {
            courseDao.updateMaterialPath(courseId, fileName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
