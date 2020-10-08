package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        Map<String, String> validationErrors = userService.validateUserAuthData(req.getParameterMap(), locale);

        if (validationErrors.isEmpty()) {
            try {
                String login = req.getParameter(ParameterName.LOGIN);
                String password = req.getParameter(ParameterName.PASSWORD);

                User user = userService.authenticate(login, password);

                if (user != null) {
                    LOGGER.debug("Authentication is successful");
                    req.getSession().setAttribute(ParameterName.USER, user);
                    resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
                } else {
                    LOGGER.debug("Authentication is canceled because wrong login or password");
                    req.setAttribute(ParameterName.INIT, req.getParameterMap());
                    req.setAttribute(ParameterName.ERROR,
                            resourceManager.getValue(LocaleMessage.WRONG_LOGIN_OR_PASSWORD));
                    req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error while authenticate user, try later", e);
            }
        } else {
            LOGGER.debug("Authentication is canceled because login or password are invalid");
            req.setAttribute(ParameterName.INIT, req.getParameterMap());
            req.setAttribute(ParameterName.ERRORS, validationErrors);
            req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
        }
    }
}
