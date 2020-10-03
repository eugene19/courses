package by.epamtc.courses.controller.command;

import by.epamtc.courses.entity.ParameterName;
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

    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(ParameterName.LOGIN);
        String password = req.getParameter(ParameterName.PASSWORD);

        if (login == null || password == null) {
            LOGGER.debug("Authentication is canceled because login or password is empty.");
            // todo send error field is empty
            sendErrorLoginOrPassword(req, resp);
            return;
        }

        User user;

        try {
            user = userService.authenticate(login, password);
        } catch (ServiceException e) {
            LOGGER.error("Error while authenticate user, try later.", e);
            // todo send error 'something goes wrong'
            return;
        }

        if (user != null) {
            req.getSession().setAttribute(ParameterName.USER, user);
            resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
        } else {
            LOGGER.debug("Authentication is canceled because wrong login or password.");
            // todo send error 'wrong login or password'
            sendErrorLoginOrPassword(req, resp);
        }
    }

    private void sendErrorLoginOrPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        String lang = (String) req.getSession().getAttribute(ParameterName.LOCALE);
        Locale locale = (lang == null) ? Locale.getDefault() : new Locale(lang);
        ResourceManager resourceManager = new ResourceManager(locale);

        req.setAttribute(ParameterName.INIT, parameterMap);
        req.setAttribute(ParameterName.ERROR, resourceManager.getValue("login.error.wrongLoginOrPass"));
        req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
    }
}
