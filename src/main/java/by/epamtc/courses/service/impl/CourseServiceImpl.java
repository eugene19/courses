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

public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
    private CourseResultDao courseResultDao = DaoProvider.getInstance().getCourseResultDao();

    @Override
    public List<Course> takeAllCourses() throws ServiceException {
        try {
            return courseDao.findAllCourses();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Course, CourseResult> takeCoursesWithResultForStudent(int userId) throws ServiceException {
        Map<Course, CourseResult> coursesWithResults = new HashMap<>();

        try {
            List<Course> courses = courseDao.findAllCoursesWithResultsForStudent(userId);

            for (Course course : courses) {
                CourseResult courseResult = courseResultDao.takeCourseResult(userId, course.getId());

                coursesWithResults.put(course, courseResult);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return coursesWithResults;
    }

    @Override
    public List<Course> takeCoursesWithStatus(CourseStatus status) throws ServiceException {
        try {
            return courseDao.findCoursesWithStatus(status);
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
            courseDao.create(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Course getCourse(int courseId) throws ServiceException {
        try {
            return courseDao.findCourseById(courseId);
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
            UserCourseStatus userCourseStatus = courseDao.takeUserCourseStatus(userId, courseId);
            if (userCourseStatus == null) {
                courseDao.addStudentApplicationOnCourse(userId, courseId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void leaveUserFromCourse(int userId, int courseId) throws ServiceException {
        try {
            courseDao.leaveStudentFromCourse(userId, courseId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserCourseStatus takeUserCourseStatus(int userId, int courseId) throws ServiceException {
        try {
            return courseDao.takeUserCourseStatus(userId, courseId);
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

    @Override
    public void updateCourseMaterialPath(int courseId, String fileName) throws ServiceException {
        try {
            courseDao.updateCourseMaterialPath(courseId, fileName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
