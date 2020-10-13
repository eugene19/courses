package by.epamtc.courses.service;

import by.epamtc.courses.service.impl.CourseResultServiceImpl;
import by.epamtc.courses.service.impl.CourseServiceImpl;
import by.epamtc.courses.service.impl.UserServiceImpl;

public final class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final CourseResultService courseResultService = new CourseResultServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public CourseResultService getCourseResultService() {
        return courseResultService;
    }
}
