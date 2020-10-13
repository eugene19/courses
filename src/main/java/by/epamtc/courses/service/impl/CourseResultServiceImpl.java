package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseResultDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.service.CourseResultService;
import by.epamtc.courses.service.ServiceException;
import org.apache.log4j.Logger;

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
}
