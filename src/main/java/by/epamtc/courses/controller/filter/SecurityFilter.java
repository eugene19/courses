package by.epamtc.courses.controller.filter;

import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    private static final int ERROR_PERMISSION_DENIED = 403;

    private Map<CommandName, UserRole[]> authorizationMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        authorizationMap.put(CommandName.GET_PROFILE_PAGE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(CommandName.GET_EDIT_PROFILE_PAGE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(CommandName.EDIT_PROFILE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(CommandName.UPLOAD_USER_PHOTO, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(CommandName.GET_ADD_COURSE_PAGE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(CommandName.ADD_COURSE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(CommandName.EDIT_COURSE, new UserRole[]{UserRole.LECTURER});
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String commandName = req.getParameter(ParameterName.COMMAND);
        User user = (User) req.getSession(true).getAttribute(ParameterName.USER);

        CommandName command = parseCommand(commandName);

        if (isCommandNeedAuthorization(command) && !hasUserAllowedRole(command, user)) {
            LOGGER.warn("Authorization fail: try opening private page user without needed permission");
            resp.sendError(ERROR_PERMISSION_DENIED);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private CommandName parseCommand(String command) {
        CommandName commandName = null;

        if (command != null) {
            try {
                commandName = CommandName.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.error("No such command in enum CommandName: " + command, e);
            }
        }

        return commandName;
    }

    private boolean isCommandNeedAuthorization(CommandName command) {
        return command != null && authorizationMap.containsKey(command);
    }

    private boolean hasUserAllowedRole(CommandName command, User user) {
        if (user == null) {
            LOGGER.debug("Authorization fail: try opening private page not authored user");
            return false;
        }

        boolean hasAllowedRole = false;

        UserRole[] allowedRoles = authorizationMap.get(command);

        for (UserRole allowedRole : allowedRoles) {
            if (allowedRole == user.getRole()) {
                hasAllowedRole = true;
                break;
            }
        }

        return hasAllowedRole;
    }
}
