package by.epamtc.courses.controller.filter;

import by.epamtc.courses.constant.ErrorCode;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.epamtc.courses.controller.command.CommandName.*;

/**
 * The filter class is designed to check authorization before do some command
 *
 * @author DEA
 */
public class SecurityFilter implements Filter {
    private static final Logger logger = Logger.getLogger(SecurityFilter.class);

    /**
     * Map that stores the correspondence between the command
     * and the user role of which the execution of this command is allowed
     */
    private final Map<CommandName, UserRole[]> authorizationMap = new HashMap<>();

    /**
     * Put commands and allowed roles in Map
     *
     * @param filterConfig <code>FilterConfig</code> object containing the
     *                     filter's configuration and initialization parameters
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // pages command
        authorizationMap.put(GET_ADD_COURSE_PAGE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(GET_COURSE_RESULT_PAGE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(GET_EDIT_COURSE_PAGE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(GET_EDIT_PROFILE_PAGE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(GET_PROFILE_PAGE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(GET_STUDENTS_RESULT_PAGE, new UserRole[]{UserRole.STUDENT});

        // user command
        authorizationMap.put(APPLY_ON_COURSE, new UserRole[]{UserRole.STUDENT});
        authorizationMap.put(EDIT_PROFILE, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});
        authorizationMap.put(LEAVE_FROM_COURSE, new UserRole[]{UserRole.STUDENT});
        authorizationMap.put(UPLOAD_USER_PHOTO, new UserRole[]{UserRole.STUDENT, UserRole.LECTURER});

        // course command
        authorizationMap.put(ADD_COURSE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(EDIT_COURSE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(FINISH_COURSE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(GET_COURSE_MATERIALS, new UserRole[]{UserRole.LECTURER, UserRole.STUDENT});
        authorizationMap.put(SET_COURSE_MARK, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(START_COURSE, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(UPDATE_USER_ON_COURSE_STATUS, new UserRole[]{UserRole.LECTURER});
        authorizationMap.put(UPLOAD_COURSE_MATERIALS, new UserRole[]{UserRole.LECTURER});
    }

    /**
     * Checks if the command can be executed by the client. If client can execute
     * filter chain continuous, else send error 'permission denied'
     *
     * @param servletRequest  the <code>ServletRequest</code> object contains the client's request
     * @param servletResponse the <code>ServletResponse</code> object contains the filter's response
     * @param filterChain     the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String commandName = req.getParameter(ParameterName.COMMAND);
        User user = (User) req.getSession(true).getAttribute(ParameterName.USER);

        CommandName command = parseCommand(commandName);

        if (isCommandNeedAuthorization(command) && !hasUserAllowedRole(command, user)) {
            logger.warn("Authorization fail: try opening private page by user without needed permission");
            resp.sendError(ErrorCode.PERMISSION_DENIED);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Parse string command name to object of <code>CommandName</code> class
     *
     * @param commandName name of command
     * @return object of <code>CommandName</code> class witch match commandName
     */
    private CommandName parseCommand(String commandName) {
        try {
            return CommandName.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("No such command in enum CommandName: " + commandName, e);
            return null;
        }
    }

    /**
     * Check if command is need authorization
     *
     * @param command command which is checked
     * @return if command need authorization - true, else - false
     */
    private boolean isCommandNeedAuthorization(CommandName command) {
        return command != null && authorizationMap.containsKey(command);
    }

    /**
     * Check if user has allowed role to do command
     *
     * @param command command which is checked
     * @param user    object of client-user
     * @return if user has allowed role - true, else - false
     */
    private boolean hasUserAllowedRole(CommandName command, User user) {
        if (user == null) {
            logger.warn("Authorization fail: try opening private page not authored user");
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
