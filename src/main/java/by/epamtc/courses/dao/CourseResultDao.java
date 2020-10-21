package by.epamtc.courses.dao;

import by.epamtc.courses.entity.CourseResult;

public interface CourseResultDao {

    void setCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;

    CourseResult takeCourseResult(int userId, int courseId) throws DaoException;

    void updateCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;
}
