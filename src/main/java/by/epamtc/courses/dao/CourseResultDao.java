package by.epamtc.courses.dao;

import by.epamtc.courses.entity.CourseResult;

/**
 * Interface of course result dao layer
 *
 * @author DEA
 */
public interface CourseResultDao {

    /**
     * Set student's course result
     *
     * @param studentId id of student whom set result
     * @param courseId  id of course to set result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws DaoException if an dao exception occurred while processing
     */
    void addCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;

    /**
     * Take course result for some student
     *
     * @param studentId id of student whom take result
     * @param courseId  id of course to take result
     * @return student's result at course
     * @throws DaoException if an dao exception occurred while processing
     */
    CourseResult takeCourseResult(int studentId, int courseId) throws DaoException;

    /**
     * Update student's course result
     *
     * @param studentId id of student whom update result
     * @param courseId  id of course to update result
     * @param mark      value of mark
     * @param comment   value of comment
     * @throws DaoException if an dao exception occurred while processing
     */
    void updateCourseResult(int studentId, int courseId, int mark, String comment) throws DaoException;
}
