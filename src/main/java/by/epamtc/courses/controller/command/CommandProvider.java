package by.epamtc.courses.controller.command;

import by.epamtc.courses.controller.command.course.*;
import by.epamtc.courses.controller.command.page.*;
import by.epamtc.courses.controller.command.user.*;

import java.util.HashMap;
import java.util.Map;

import static by.epamtc.courses.controller.command.CommandName.*;

/**
 * Class that provides access to command objects
 *
 * @author DEA
 */
public final class CommandProvider {

    /**
     * Map that matches the name of a command with an object that implements this command
     */
    private final Map<CommandName, Command> commands = new HashMap<>();

    /**
     * Construct a CommandProvider and put commands
     */
    public CommandProvider() {
        // go to page commands
        commands.put(GET_ABOUT_US_PAGE, new AboutPageCommand());
        commands.put(GET_ABOUT_USER_PAGE, new AboutUserPageCommand());
        commands.put(GET_ADD_COURSE_PAGE, new AddCoursePageCommand());
        commands.put(GET_COURSE_DETAILS_PAGE, new CourseDetailsPageCommand());
        commands.put(GET_COURSES_PAGE, new CoursesPageCommand());
        commands.put(GET_CONTACT_PAGE, new ContactPageCommand());
        commands.put(GET_EDIT_COURSE_PAGE, new EditCoursePageCommand());
        commands.put(GET_EDIT_PROFILE_PAGE, new EditProfilePageCommand());
        commands.put(GET_COURSE_RESULT_PAGE, new CourseResultPageCommand());
        commands.put(GET_LOGIN_PAGE, new LoginPageCommand());
        commands.put(GET_NEWS_PAGE, new NewsPageCommand());
        commands.put(GET_PROFILE_PAGE, new ProfilePageCommand());
        commands.put(GET_REGISTRATION_PAGE, new RegistrationPageCommand());
        commands.put(GET_STUDENTS_RESULT_PAGE, new StudentResultsPageCommand());

        // user commands
        commands.put(EDIT_PROFILE, new EditProfileCommand());
        commands.put(LOGIN, new LoginCommand());
        commands.put(LOGOUT, new LogoutCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
        commands.put(SET_COURSE_MARK, new SetCourseResultCommand());
        commands.put(UPLOAD_USER_PHOTO, new UploadPhotoCommand());
        commands.put(UPDATE_USER_ON_COURSE_STATUS, new UpdateUserCourseStatusCommand());

        // course commands
        commands.put(ADD_COURSE, new CreateCourseCommand());
        commands.put(EDIT_COURSE, new EditCourseCommand());
        commands.put(APPLY_ON_COURSE, new ApplyOnCourseCommand());
        commands.put(FILTER_COURSES, new FilterCoursesCommand());
        commands.put(FINISH_COURSE, new FinishCourseCommand());
        commands.put(GET_COURSE_MATERIALS, new GetCourseMaterialsCommand());
        commands.put(LEAVE_FROM_COURSE, new LeaveFromCourseCommand());
        commands.put(START_COURSE, new StartCourseCommand());
        commands.put(UPLOAD_COURSE_MATERIALS, new UploadCourseMaterialsCommand());

        // other commands
        commands.put(LOCALE, new LocaleCommand());
    }

    /**
     * Finds and returns a command by name
     *
     * @param command name of command
     * @return object that implements this command
     */
    public Command getCommand(String command) {
        String commandNameUpper = command.toUpperCase();
        CommandName commandName = CommandName.valueOf(commandNameUpper);

        return commands.get(commandName);
    }
}
