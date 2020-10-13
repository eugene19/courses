package by.epamtc.courses.service;

import by.epamtc.courses.entity.CourseResult;

public interface CourseResultService {

    CourseResult getCourseResultForUserByCourse(int userId, int courseId) throws ServiceException;
}
