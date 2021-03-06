package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing authorization of client
 *
 * @author DEA
 */
public class LoginCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of 'Log in' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Map<String, String[]> parameters = req.getParameterMap();

        // check if user already authored
        Object alreadyAuthoredUser = session.getAttribute(ParameterName.USER);
        if (alreadyAuthoredUser != null) {
            logger.warn("Try login again by already authored user");
            resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
            return;
        }

        Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        // check inputted auth data
        Map<String, String> validationErrors = userService.validateUserAuthData(parameters, locale);
        if (!validationErrors.isEmpty()) {
            logger.debug("Authentication is canceled because login or password are invalid");
            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR,
                    resourceManager.getValue(LocaleMessage.WRONG_LOGIN_OR_PASSWORD));
            req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
            return;
        }

        try {
            String login = req.getParameter(ParameterName.LOGIN);
            String password = req.getParameter(ParameterName.PASSWORD);

            User user = userService.authenticate(login, password);

            // check if user not found
            if (user == null) {
                logger.debug("Authentication is canceled because wrong login or password");
                req.setAttribute(ParameterName.INIT, parameters);
                req.setAttribute(ParameterName.ERROR,
                        resourceManager.getValue(LocaleMessage.WRONG_LOGIN_OR_PASSWORD));
                req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
                return;
            }

            logger.debug("Authentication is successful");
            session.setAttribute(ParameterName.USER, user);
            resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
        } catch (ServiceException e) {
            logger.error("Error while authenticate user, try later", e);
            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
        }
    }
}
