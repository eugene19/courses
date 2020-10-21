package by.epamtc.courses.dao;

import by.epamtc.courses.dao.impl.SqlCourseDao;
import by.epamtc.courses.dao.impl.SqlCourseResultDao;
import by.epamtc.courses.dao.impl.SqlUserDao;

/**
 * Class that provides access to dao objects
 *
 * @author DEA
 */
public final class DaoProvider {

    /**
     * Provider instance
     */
    private static final DaoProvider instance = new DaoProvider();

    /**
     * User dao instance
     */
    private final UserDao userDao = new SqlUserDao();

    /**
     * Course dao instance
     */
    private final CourseDao courseDao = new SqlCourseDao();

    /**
     * Course result dao instance
     */
    private final CourseResultDao courseResultDao = new SqlCourseResultDao();

    /**
     * Construct a DaoProvider
     */
    private DaoProvider() {
    }

    /**
     * @return instance of DaoProvider
     */
    public static DaoProvider getInstance() {
        return instance;
    }

    /**
     * @return instance of user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * @return instance of course dao
     */
    public CourseDao getCourseDao() {
        return courseDao;
    }

    /**
     * @return instance of course result dao
     */
    public CourseResultDao getCourseResultDao() {
        return courseResultDao;
    }
}
