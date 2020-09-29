package by.epamtc.courses.controller.command;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.UserDaoImpl;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.i18n.ResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LoginCommand implements Command {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String INIT_ATTRIBUTE = "init";
    private static final String ERROR_ATTRIBUTE = "error";

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(LOGIN_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);
        UserDao userDao = new UserDaoImpl();

        if (login != null && password != null) {
            try {
                User user = userDao.getByLogin(login);
                if (user != null && user.getPassword().equals(password)) {
                    req.getSession().setAttribute(USER_ATTRIBUTE, user);
                    resp.sendRedirect("/");
                } else {
                    sendWrongLoginOrPassword(req, resp);
                }
            } catch (DaoException e) {
                //log
                e.printStackTrace();
            }
        } else {
            sendWrongLoginOrPassword(req, resp);
        }
    }

    private void sendWrongLoginOrPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        String lang = (String) req.getSession().getAttribute(LOCALE_ATTRIBUTE);
        Locale locale = (lang == null) ? Locale.getDefault() : new Locale(lang);
        ResourceManager resourceManager = new ResourceManager(locale);

        req.setAttribute(INIT_ATTRIBUTE, parameterMap);
        req.setAttribute(ERROR_ATTRIBUTE, resourceManager.getValue("login.error.wrongLoginOrPass"));
        req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
    }
}
