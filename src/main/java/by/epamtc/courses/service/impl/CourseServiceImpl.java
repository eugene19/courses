package by.epamtc.courses.service.impl;

import by.epamtc.courses.dao.CourseDao;
import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.DaoProvider;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.validation.CourseValidator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @Override
    public List<Course> takeCoursesWithStatus(CourseStatus status) throws ServiceException {
        try {
            return courseDao.takeCoursesWithStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void createNew(Course course) throws ServiceException {
        try {
            courseDao.createNew(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Course getCourse(int courseId) throws ServiceException {
        try {
            return courseDao.getCourse(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Course course) throws ServiceException {
        try {
            courseDao.update(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void enterUserOnCourse(int userId, int courseId) throws ServiceException {
        try {
            UserCourseStatus userCourseStatus = courseDao.getUserCourseStatus(userId, courseId);
            if (userCourseStatus == null) {
                courseDao.enterUserOnCourse(userId, courseId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void leaveUserFromCourse(int userId, int courseId) throws ServiceException {
        try {
            courseDao.leaveUserFromCourse(userId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserCourseStatus getUserCourseStatus(int userId, int courseId) throws ServiceException {
        try {
            return courseDao.getUserCourseStatus(userId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatus(int courseId, CourseStatus courseStatus) throws ServiceException {
        try {
            courseDao.updateStatus(courseId, courseStatus);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
