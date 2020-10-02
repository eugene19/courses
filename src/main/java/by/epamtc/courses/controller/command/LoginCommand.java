package by.epamtc.courses.controller.command;

import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
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

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String INIT_ATTRIBUTE = "init";
    private static final String ERROR_ATTRIBUTE = "error";

    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(LOGIN_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);

        if (login == null || password == null) {
            // todo send error field is empty
            sendErrorLoginOrPassword(req, resp);
            return;
        }

        User user;

        try {
            user = userService.authenticate(login, password);
        } catch (ServiceException e) {
            // todo send error 'something goes wrong'
            LOGGER.error("Error while authenticate user.", e);
            return;
        }

        if (user != null) {
            req.getSession().setAttribute(USER_ATTRIBUTE, user);
            resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
        } else {
            // todo send error 'wrong login or password'
            sendErrorLoginOrPassword(req, resp);
        }
    }

    private void sendErrorLoginOrPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        String lang = (String) req.getSession().getAttribute(LOCALE_ATTRIBUTE);
        Locale locale = (lang == null) ? Locale.getDefault() : new Locale(lang);
        ResourceManager resourceManager = new ResourceManager(locale);

        req.setAttribute(INIT_ATTRIBUTE, parameterMap);
        req.setAttribute(ERROR_ATTRIBUTE, resourceManager.getValue("login.error.wrongLoginOrPass"));
        req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
    }
}
