package by.epamtc.courses.controller.command;

import by.epamtc.courses.controller.command.page.*;
import by.epamtc.courses.controller.command.user.EditProfileCommand;
import by.epamtc.courses.controller.command.user.LoginCommand;
import by.epamtc.courses.controller.command.user.LogoutCommand;
import by.epamtc.courses.controller.command.user.RegistrationCommand;

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
    }

    public Command getCommand(String command) {
        String commandNameUpper = command.toUpperCase();
        CommandName commandName = CommandName.valueOf(commandNameUpper);
        return commands.get(commandName);
    }
}
