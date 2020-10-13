package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.service.CourseResultService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.validation.CourseResultValidator;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;

public class CourseResultServiceImpl implements CourseResultService {
    private static final Logger LOGGER = Logger.getLogger(CourseResultServiceImpl.class);

    private CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();

    @Override
    public CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws ServiceException {
        try {
            return courseResultDao.getCourseResultForUserByCourse(userId, courseId);
        } catch (DaoException e) {
            LOGGER.error("Error while get course result for user by course", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setCourseResult(int studentId, int courseId, int mark, String comment) throws ServiceException {
        try {
            CourseResult courseResultForUserByCourse = courseResultDao.getCourseResultForUserByCourse(studentId, courseId);

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
    public Map<String, String> validateCourseResult(Map<String, String[]> parameters, Locale locale) {
        CourseResultValidator validator = new CourseResultValidator(parameters, locale);

        return validator
                .validateMark()
                .validateDescription()
                .getErrors();
    }
}
