package by.epamtc.courses.controller.command;

import by.epamtc.courses.controller.command.course.AddCourseCommand;
import by.epamtc.courses.controller.command.page.*;
import by.epamtc.courses.controller.command.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GET_NEWS_PAGE, new NewsPageCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.GET_LOGIN_PAGE, new LoginPageCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.LOCALE, new LocaleCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.GET_REGISTRATION_PAGE, new RegistrationPageCommand());
        commands.put(CommandName.GET_PROFILE_PAGE, new ProfilePageCommand());
        commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
        commands.put(CommandName.GET_EDIT_PROFILE_PAGE, new EditProfilePageCommand());
        commands.put(CommandName.UPLOAD_USER_PHOTO, new UploadPhotoCommand());
        commands.put(CommandName.GET_COURSES_PAGE, new CoursesPageCommand());
        commands.put(CommandName.GET_ADD_COURSE_PAGE, new AddCoursePageCommand());
        commands.put(CommandName.ADD_COURSE, new AddCourseCommand());
        commands.put(CommandName.EDIT_COURSE, new EditCoursePageCommand());
    }

    public Command getCommand(String command) {
        String commandNameUpper = command.toUpperCase();
        CommandName commandName = CommandName.valueOf(commandNameUpper);
        return commands.get(commandName);
    }
}
