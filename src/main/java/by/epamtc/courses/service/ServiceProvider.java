package by.epamtc.courses.service;

import by.epamtc.courses.service.impl.CourseResultServiceImpl;
import by.epamtc.courses.service.impl.CourseServiceImpl;
import by.epamtc.courses.service.impl.UserServiceImpl;

/**
 * Class that provides access to service objects
 *
 * @author DEA
 */
public final class ServiceProvider {

    /**
     * Provider instance
     */
    private static final ServiceProvider instance = new ServiceProvider();

    /**
     * User service instance
     */
    private final UserService userService = new UserServiceImpl();

    /**
     * Course service instance
     */
    private final CourseService courseService = new CourseServiceImpl();

    /**
     * Course result service instance
     */
    private final CourseResultService courseResultService = new CourseResultServiceImpl();

    /**
     * Construct a ServiceProvider
     */
    private ServiceProvider() {
    }

    /**
     * @return instance of ServiceProvider
     */
    public static ServiceProvider getInstance() {
        return instance;
    }

    /**
     * @return instance of user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * @return instance of course service
     */
    public CourseService getCourseService() {
        return courseService;
    }

    /**
     * @return instance of course result service
     */
    public CourseResultService getCourseResultService() {
        return courseResultService;
    }
}
