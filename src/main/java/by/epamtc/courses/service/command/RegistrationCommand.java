package by.epamtc.courses.service.command;

import by.epamtc.courses.dao.DaoException;
import by.epamtc.courses.dao.UserDao;
import by.epamtc.courses.dao.impl.UserDaoImpl;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.builder.UserBuilder;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.validation.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RegistrationCommand implements Command {

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        String lang = (String) req.getSession().getAttribute("locale");
        UserValidator userValidator = new UserValidator(parameterMap, lang);

        if (userValidator.validateAll().isValid()) {
            UserBuilder builder = new UserBuilder();
            User user = builder.createFromParams(parameterMap);
            UserDao userDao = new UserDaoImpl();
            try {
                userDao.insert(user);
            } catch (DaoException e) {
                //log
                resp.sendError(500);
            }
            resp.sendRedirect("/");
        } else {
            Map<String, String> errors = userValidator.getErrors();
            req.setAttribute("init", parameterMap);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(req, resp);
        }
    }
}
