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

public class CourseResultServiceImpl implements CourseResultService {

    private CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();
    private UserDao userDao = DaoProvider.getInstance().getUserDao();

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

    @Override
    public CourseResult takeCourseResultForUser(int studentId, int courseId) throws ServiceException {
        try {
            return courseResultDao.takeCourseResult(studentId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale) {
        CourseResultValidator validator = new CourseResultValidator(parameters, locale);

        return validator
                .validateMark()
                .validateDescription()
                .getErrors();
    }
}
