package by.epamtc.courses.dao;

import by.epamtc.courses.entity.CourseResult;

public interface CourseResultDao {

    CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws DaoException;

    void setCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;

    void updateCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;
}
