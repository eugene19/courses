package by.epamtc.courses.dao;

import by.epamtc.courses.dao.impl.SqlUserDao;

public final class DaoProvider {

    private static final DaoProvider instance = new DaoProvider();

    private final UserDao userDao = new SqlUserDao();
//    private final CourseDao courseDao = new SqlCourseDao();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

//    public CourseDao getCourseDao() {
//        return courseDao;
//    }
}
