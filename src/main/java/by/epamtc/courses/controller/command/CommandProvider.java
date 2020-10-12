package by.epamtc.courses.controller.command;

import by.epamtc.courses.controller.command.course.*;
import by.epamtc.courses.controller.command.page.*;
import by.epamtc.courses.controller.command.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        // go to page commands
        commands.put(CommandName.GET_ABOUT_US_PAGE, new AboutPageCommand());
        commands.put(CommandName.GET_ADD_COURSE_PAGE, new AddCoursePageCommand());
        commands.put(CommandName.GET_COURSE_DETAILS_PAGE, new CourseDetailsPageCommand());
        commands.put(CommandName.GET_COURSES_PAGE, new CoursesPageCommand());
        commands.put(CommandName.GET_CONTACT_PAGE, new ContactPageCommand());
        commands.put(CommandName.GET_EDIT_COURSE_PAGE, new EditCoursePageCommand());
        commands.put(CommandName.GET_EDIT_PROFILE_PAGE, new EditProfilePageCommand());
        commands.put(CommandName.GET_COURSE_MARK_PAGE, new CourseMarkPageCommand());
        commands.put(CommandName.GET_LOGIN_PAGE, new LoginPageCommand());
        commands.put(CommandName.GET_NEWS_PAGE, new NewsPageCommand());
        commands.put(CommandName.GET_PROFILE_PAGE, new ProfilePageCommand());
        commands.put(CommandName.GET_REGISTRATION_PAGE, new RegistrationPageCommand());

        // user commands
        commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.SET_COURSE_MARK, new SetCourseResultCommand());
        commands.put(CommandName.UPLOAD_USER_PHOTO, new UploadPhotoCommand());
        commands.put(CommandName.UPDATE_USER_ON_COURSE_STATUS, new UpdateUserCourseStatusCommand());

        // course commands
        commands.put(CommandName.ADD_COURSE, new AddCourseCommand());
        commands.put(CommandName.EDIT_COURSE, new EditCourseCommand());
        commands.put(CommandName.ENTER_ON_COURSE, new EnterOnCourseCommand());
        commands.put(CommandName.LEAVE_FROM_COURSE, new LeaveFromCourseCommand());
        commands.put(CommandName.UPDATE_COURSE_STATUS, new UpdateCourseStatusCommand());

        // other commands
        commands.put(CommandName.LOCALE, new LocaleCommand());
    }

    public Command getCommand(String command) {
        String commandNameUpper = command.toUpperCase();
        CommandName commandName = CommandName.valueOf(commandNameUpper);
        return commands.get(commandName);
    }
}
