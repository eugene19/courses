package by.epamtc.courses.dao;

import by.epamtc.courses.entity.CourseResult;

public interface CourseResultDao {

    CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws DaoException;
}
