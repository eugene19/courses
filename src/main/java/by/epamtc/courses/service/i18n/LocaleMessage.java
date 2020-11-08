package by.epamtc.courses.service.i18n;

/**
 * Class containing constants with keys of localize messages from property
 *
 * @author DEA
 */
public final class LocaleMessage {

    // user messages

    /**
     * Key of error message 'Date after max'
     */
    public static final String ERROR_DATE_AFTER_MAX = "user.error.dateAfterMax";

    /**
     * Key of error message 'Date before min'
     */
    public static final String ERROR_DATE_BEFORE_MIN = "user.error.dateBeforeMin";

    /**
     * Key of error message 'Field is empty'
     */
    public static final String ERROR_FIELD_EMPTY = "error.fieldIsEmpty";

    /**
     * Key of error message 'Incorrect date'
     */
    public static final String ERROR_INCORRECT_DATE = "user.error.incorrectDate";

    /**
     * Key of error message 'Incorrect email'
     */
    public static final String ERROR_INCORRECT_EMAIL = "user.error.incorrectEmail";

    /**
     * Key of error message 'Incorrect login'
     */
    public static final String ERROR_INCORRECT_LOGIN = "user.error.incorrectLogin";

    /**
     * Key of error message 'Incorrect name'
     */
    public static final String ERROR_INCORRECT_NAME = "user.error.incorrectName";

    /**
     * Key of error message 'Incorrect password'
     */
    public static final String ERROR_INCORRECT_PASSWORD = "user.error.incorrectPassword";

    /**
     * Key of error message 'Incorrect role'
     */
    public static final String ERROR_INCORRECT_ROLE = "user.error.incorrectRole";

    /**
     * Key of error message 'Incorrect surname'
     */
    public static final String ERROR_INCORRECT_SURNAME = "user.error.incorrectSurname";

    /**
     * Key of error message 'Wrong login or password'
     */
    public static final String WRONG_LOGIN_OR_PASSWORD = "login.error.wrongLoginOrPass";

    /**
     * Key of error message 'File is not chosen'
     */
    public static final String ERROR_INCORRECT_FILE_NAME = "user.error.fileIsEmpty";

    // course messages

    /**
     * Key of error message 'Incorrect summary'
     */
    public static final String ERROR_INCORRECT_SUMMARY = "course.error.summary";

    /**
     * Key of error message 'Incorrect description'
     */
    public static final String ERROR_INCORRECT_DESCRIPTION = "course.error.description";

    /**
     * Key of error message 'End date should be after start date'
     */
    public static final String ERROR_END_DATE_AFTER_START = "course.error.endDateAfterStart";

    /**
     * Key of error message 'Incorrect student limit'
     */
    public static final String ERROR_INCORRECT_STUDENTS_LIMIT = "course.error.studentsLimit";

    /**
     * Key of error message 'Date before min'
     */
    public static final String ERROR_COURSE_DATE_BEFORE_MIN = "course.error.dateBeforeMin";

    /**
     * Key of error message 'Date after max'
     */
    public static final String ERROR_COURSE_DATE_AFTER_MAX = "course.error.dateAfterMax";

    /**
     * Key of error message 'Incorrect mark'
     */
    public static final String ERROR_INCORRECT_MARK = "course.result.error.incorrectMark";

    /**
     * Key of error message 'Incorrect comment'
     */
    public static final String ERROR_INCORRECT_COMMENT = "course.result.error.incorrectDescription";

    /**
     * Key of error message 'For finish course set all students results'
     */
    public static final String ERROR_SET_RESULTS = "message.course.finish.warning";

    /**
     * Key of error message 'It's no possible to enter student because student limit'
     */
    public static final String ERROR_UPDATE_STATUS_STUDENTS_LIMIT = "error.update.userCourse.status.studentsLimit";

    /**
     * Key of error message 'Course has not uploaded materials'
     */
    public static final String NO_MATERIALS = "course.error.noMaterials";

    // general messages

    /**
     * Key of error message 'Something goes wrong'
     */
    public static final String SOMETHING_GOES_WRONG = "errorPage.message";

    /**
     * Private constructor to forbid creation of objects
     */
    private LocaleMessage() {
    }
}
