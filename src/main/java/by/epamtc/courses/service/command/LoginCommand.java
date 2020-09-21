package by.epamtc.courses.service.command;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.UserDaoImpl;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.i18n.ResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LoginCommand implements Command {

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDao userDao = new UserDaoImpl();

        if (login != null && password != null) {
            try {
                User user = userDao.getByLogin(login);
                if (user != null && user.getPassword().equals(password)) {
                    req.getSession().setAttribute("user", user);
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
        String lang = (String) req.getSession().getAttribute("locale");
        Locale locale = (lang == null) ? Locale.getDefault() : new Locale(lang);
        ResourceManager resourceManager = new ResourceManager(locale);

        req.setAttribute("init", parameterMap);
        req.setAttribute("error", resourceManager.getValue("login.error.wrongLoginOrPass"));
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
