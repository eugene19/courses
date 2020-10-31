package by.epamtc.courses.service;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;

/**
 * Class containing path to jsp pages
 *
 * @author DEA
 */
public final class PageName {

    /**
     * Path to 'About us' jsp page
     */
    public static final String ABOUT_US_PAGE = "WEB-INF/jsp/about.jsp";

    /**
     * Path to 'About user' jsp page
     */
    public static final String ABOUT_USER_PAGE = "WEB-INF/jsp/user/about_user.jsp";

    /**
     * Path to 'Add course' jsp page
     */
    public static final String ADD_COURSE_PAGE = "WEB-INF/jsp/course/add_course.jsp";

    /**
     * Path to 'Contact us' jsp page
     */
    public static final String CONTACT_PAGE = "WEB-INF/jsp/contact.jsp";

    /**
     * Path to 'Course details' jsp page
     */
    public static final String COURSE_DETAILS_PAGE = "WEB-INF/jsp/course/course_details.jsp";

    /**
     * Path to 'Course list' jsp page
     */
    public static final String COURSES_PAGE = "WEB-INF/jsp/course/courses.jsp";

    /**
     * Path to 'Edit course' jsp page
     */
    public static final String EDIT_COURSE_PAGE = "WEB-INF/jsp/course/edit_course.jsp";

    /**
     * Path to 'Edit profile' jsp page
     */
    public static final String EDIT_PROFILE_PAGE = "WEB-INF/jsp/user/edit_profile.jsp";

    /**
     * Path to 'Course result' jsp page
     */
    public static final String COURSE_MARK_PAGE = "WEB-INF/jsp/course/course_mark.jsp";

    /**
     * Path to 'Login' jsp page
     */
    public static final String LOGIN_PAGE = "WEB-INF/jsp/user/login.jsp";

    /**
     * Path to 'News' jsp page
     */
    public static final String NEWS_PAGE = "WEB-INF/jsp/news.jsp";

    /**
     * Path to 'Profile' jsp page
     */
    public static final String PROFILE_PAGE = "WEB-INF/jsp/user/profile.jsp";

    /**
     * Path to 'Registration' jsp page
     */
    public static final String REGISTRATION_PAGE = "WEB-INF/jsp/user/registration.jsp";

    /**
     * Path to 'Student's result' jsp page
     */
    public static final String STUDENT_RESULTS = "WEB-INF/jsp/user/student_result.jsp";

    /**
     * Url to default page
     */
    public static final String DEFAULT_PAGE_URL = "/";

    /**
     * Url to main servlet
     */
    public static final String MAIN_SERVLET_URL = "/main";

    /**
     * Url to 'Course list' page
     */
    public static final String COURSES_URL = MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSES_PAGE;

    /**
     * Url to 'Course details' page
     */
    public static final String COURSE_DETAILS_URL = MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE
            + URLConstant.PARAMETERS_SEPARATOR
            + ParameterName.COURSE_ID + URLConstant.KEY_VALUE_SEPARATOR;

    /**
     * Url to 'Profile' page
     */
    public static final String PROFILE_URL = PageName.MAIN_SERVLET_URL
            + URLConstant.START_PARAMETERS
            + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_PROFILE_PAGE;

    /**
     * Url to uploaded files, where
     * %d - id of user who upload file and
     * %s - name of file
     */
    public static final String UPLOAD_FILES_FORMAT_URL = "/uploadFiles/%d/%s";
}
