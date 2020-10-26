package by.epamtc.courses.controller.command;

/**
 * Enum containing all commands
 *
 * @author DEA
 */
public enum CommandName {

    // go to page commands

    /**
     * Command to open 'About us' page
     */
    GET_ABOUT_US_PAGE,

    /**
     * Command to open 'Add course' page
     */
    GET_ADD_COURSE_PAGE,

    /**
     * Command to open 'Contact us' page
     */
    GET_CONTACT_PAGE,

    /**
     * Command to open 'Course details' page
     */
    GET_COURSE_DETAILS_PAGE,

    /**
     * Command to open 'Course list' page
     */
    GET_COURSES_PAGE,

    /**
     * Command to open 'Edit course' page
     */
    GET_EDIT_COURSE_PAGE,

    /**
     * Command to open 'Edit profile' page
     */
    GET_EDIT_PROFILE_PAGE,

    /**
     * Command to open 'Course result' page
     */
    GET_COURSE_RESULT_PAGE,

    /**
     * Command to open 'Login' page
     */
    GET_LOGIN_PAGE,

    /**
     * Command to open 'News' page
     */
    GET_NEWS_PAGE,

    /**
     * Command to open 'Profile' page
     */
    GET_PROFILE_PAGE,

    /**
     * Command to open 'Registration' page
     */
    GET_REGISTRATION_PAGE,

    /**
     * Command to open 'Student's result' page
     */
    GET_STUDENTS_RESULT_PAGE,

    // user commands

    /**
     * Command to edit (update) user's profile
     */
    EDIT_PROFILE,

    /**
     * Command to log in user
     */
    LOGIN,

    /**
     * Command to log out user
     */
    LOGOUT,

    /**
     * Command to register user
     */
    REGISTRATION,

    /**
     * Command to set course result for students
     */
    SET_COURSE_MARK,

    /**
     * Command to upload (save new) user's photo
     */
    UPLOAD_USER_PHOTO,

    /**
     * Command to update student's course status
     */
    UPDATE_USER_ON_COURSE_STATUS,

    // course commands

    /**
     * Command to add (create) new course
     */
    ADD_COURSE,

    /**
     * Command to edit (update) existing course
     */
    EDIT_COURSE,

    /**
     * Command to save student's application for course
     */
    APPLY_ON_COURSE,

    /**
     * Command to add filtration for courses
     */
    FILTER_COURSES,

    /**
     * Command to finish course
     */
    FINISH_COURSE,

    /**
     * Command to get course's materials
     */
    GET_COURSE_MATERIALS,

    /**
     * Command to leave student from course
     */
    LEAVE_FROM_COURSE,

    /**
     * Command to start course
     */
    START_COURSE,

    /**
     * Command to upload course's materials
     */
    UPLOAD_COURSE_MATERIALS,

    // other commands

    /**
     * Command to change language on web pages
     */
    LOCALE
}
