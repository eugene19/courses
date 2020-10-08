package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao = DaoProvider.getInstance().getCourseDao();

    @Override
    public List<Course> takeAllCourses() throws ServiceException {
        try {
            return courseDao.takeAllCourses();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
