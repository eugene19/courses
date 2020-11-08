package by.epamtc.courses.service.impl;

import by.epamtc.courses.constant.ParameterName;
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

import java.util.*;

/**
 * Implementation of course service layer
 *
 * @author DEA
 */
public class CourseServiceImpl implements CourseService {

    /**
     * Count of courses on page
     */
    private static final int PAGE_ITEMS_COUNT = 5;

    /**
     * Delimiter of statuses
     */
    private static final String STATUS_DELIMITER = ", ";

    /**
     * Constant character 'Single quote'
     */
    private static final char SINGLE_QUOTE = '\'';

    /**
     * Instance of course dao
     */
    private final CourseDao courseDao = DaoProvider.getInstance().getCourseDao();

    /**
     * Instance of course's result dao
     */
    private final CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();

    /**
     * Count number of courses in status
     *
     * @param statuses value of status to count
     * @return number of courses with statuses
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public int countCoursesInStatus(String[] statuses) throws ServiceException {
        try {
            if (statuses == null) {
                statuses = CourseStatus.getStatusesNames(); // default value - all statuses
            }

            String formattedStatuses = formatValuesInLine(statuses);
            return courseDao.countCoursesInStatus(formattedStatuses);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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
     * Find list of courses of define statuses
     *
     * @param statuses   values of status to find
     * @param pageNumber number of page to find
     * @param sort       name of column to sort list
     * @return List of courses with define statuses
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public List<Course> findCoursesWithStatusForPage(String[] statuses, int pageNumber,
                                                     String sort) throws ServiceException {
        int offset = PAGE_ITEMS_COUNT * pageNumber;
        if (sort == null) {
            sort = ParameterName.SUMMARY; // default value
        }
        if (statuses == null) {
            statuses = CourseStatus.getStatusesNames(); // default value - all statuses
        }

        try {
            String formattedStatuses = formatValuesInLine(statuses);
            return courseDao.findCoursesWithStatusForPage(formattedStatuses, PAGE_ITEMS_COUNT, offset, sort);
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

    /**
     * Format values from array to line like: 'value1', 'value2' ... 'valueN'
     *
     * @param statuses source array to format
     * @return formatted line with all values of src array
     */
    private String formatValuesInLine(String[] statuses) {
        String[] quotedValues = takeValuesInQuote(statuses);
        return String.join(STATUS_DELIMITER, quotedValues);
    }

    /**
     * Take all values from array in single quote
     *
     * @param values array with values to take in quotes
     * @return array with old values taken in quotes
     */
    private String[] takeValuesInQuote(String[] values) {
        String[] destArray = Arrays.copyOf(values, values.length);

        for (int i = 0; i < destArray.length; i++) {
            destArray[i] = SINGLE_QUOTE + destArray[i] + SINGLE_QUOTE;
        }

        return destArray;
    }
}
