package by.epamtc.courses.service;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;

public abstract class PageName {
    public static final String ABOUT_US_PAGE = "WEB-INF/jsp/about.jsp";
    public static final String ADD_COURSE_PAGE = "WEB-INF/jsp/course/add_course.jsp";
    public static final String CONTACT_PAGE = "WEB-INF/jsp/contact.jsp";
    public static final String COURSE_DETAILS_PAGE = "WEB-INF/jsp/course/course_details.jsp";
    public static final String COURSES_PAGE = "WEB-INF/jsp/course/courses.jsp";
    public static final String EDIT_PROFILE_PAGE = "WEB-INF/jsp/user/edit_profile.jsp";
    public static final String EDIT_COURSE_PAGE = "WEB-INF/jsp/course/edit_course.jsp";
    public static final String COURSE_MARK_PAGE = "WEB-INF/jsp/course/course_mark.jsp";
    public static final String LOGIN_PAGE = "WEB-INF/jsp/user/login.jsp";
    public static final String NEWS_PAGE = "WEB-INF/jsp/news.jsp";
    public static final String PROFILE_PAGE = "WEB-INF/jsp/user/profile.jsp";
    public static final String REGISTRATION_PAGE = "WEB-INF/jsp/user/registration.jsp";
    public static final String STUDENT_RESULTS = "WEB-INF/jsp/user/student_result.jsp";

    public static final String DEFAULT_PAGE_URL = "/";
    public static final String MAIN_SERVLET_URL = "/main";
    public static final String COURSES_URL = MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSES_PAGE;
    public static final String COURSE_DETAILS_URL = MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE
            + URLConstant.PARAMETERS_SEPARATOR
            + ParameterName.COURSE_ID + URLConstant.KEY_VALUE_SEPARATOR;
    public static final String PROFILE_URL = PageName.MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_PROFILE_PAGE;
    public static final String UPLOAD_FILES_FORMAT_URL = "/uploadFiles/%d/%s";
}
