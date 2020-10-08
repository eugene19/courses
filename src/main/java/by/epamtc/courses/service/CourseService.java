package by.epamtc.courses.service;

import by.epamtc.courses.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> takeAllCourses() throws ServiceException;
}
