package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.CourseResultService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.validation.CourseResultValidator;

import java.util.Locale;
import java.util.Map;

/**
 * Implementation of course result service layer
 *
 * @author DEA
 */
public class CourseResultServiceImpl implements CourseResultService {

    /**
     * Instance of course result dao
     */
    private final CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();

    /**
     * Instance of user dao
     */
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();

    /**
     * Check if all student have results (marks) at define course
     *
     * @param courseId id of course to check results
     * @return true if all student have results, else - false
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public boolean areAllStudentsHaveResult(int courseId) throws ServiceException {
        try {
            Map<User, UserCourseStatus> userOnCourse =
                    userDao.findStudentsOnCourseWithStatus(courseId, UserCourseStatus.ENTERED);

            for (Map.Entry<User, UserCourseStatus> userCourse : userOnCourse.entrySet()) {
                int userId = userCourse.getKey().getId();
                CourseResult result = takeCourseResultForUser(userId, courseId);
                if (result == null) {
                    return false;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return true;
    }

    /**
     * Set student's course result
     *
     * @param studentId id of student whom set result
     * @param courseId  id of course to set result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public void setCourseResult(int studentId, int courseId, int mark, String comment) throws ServiceException {
        try {
            CourseResult courseResultForUserByCourse = courseResultDao.takeCourseResult(studentId, courseId);

            if (courseResultForUserByCourse == null) {
                courseResultDao.setCourseResult(studentId, courseId, mark, comment);
            } else {
                courseResultDao.updateCourseResult(studentId, courseId, mark, comment);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Take course result for some student
     *
     * @param studentId id of student whom take result
     * @param courseId  id of course to take result
     * @return student's result at course
     * @throws ServiceException if an service exception occurred while processing
     */
    @Override
    public CourseResult takeCourseResultForUser(int studentId, int courseId) throws ServiceException {
        try {
            return courseResultDao.takeCourseResult(studentId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Check if course result is valid
     *
     * @param parameters request's parameters with values from client
     * @param locale     locale of client to select messages translation
     * @return validation errors with error message as <code>Map</code>
     */
    @Override
    public Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale) {
        CourseResultValidator validator = new CourseResultValidator(parameters, locale);

        return validator
                .validateMark()
                .validateDescription()
                .getErrors();
    }
}
