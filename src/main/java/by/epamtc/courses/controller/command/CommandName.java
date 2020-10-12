package by.epamtc.courses.controller.command;

public enum CommandName {
    // go to page commands
    GET_ABOUT_US_PAGE,
    GET_ADD_COURSE_PAGE,
    GET_CONTACT_PAGE,
    GET_COURSE_DETAILS_PAGE,
    GET_COURSES_PAGE,
    GET_EDIT_COURSE_PAGE,
    GET_EDIT_PROFILE_PAGE,
    GET_COURSE_MARK_PAGE,
    GET_LOGIN_PAGE,
    GET_NEWS_PAGE,
    GET_PROFILE_PAGE,
    GET_REGISTRATION_PAGE,

    // user commands
    EDIT_PROFILE,
    LOGIN,
    LOGOUT,
    REGISTRATION,
    SET_COURSE_MARK,
    UPLOAD_USER_PHOTO,
    UPDATE_USER_ON_COURSE_STATUS,

    // course commands
    ADD_COURSE,
    EDIT_COURSE,
    ENTER_ON_COURSE,
    LEAVE_FROM_COURSE,
    UPDATE_COURSE_STATUS,

    // other commands
    LOCALE
}
