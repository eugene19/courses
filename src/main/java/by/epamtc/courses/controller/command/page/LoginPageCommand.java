package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginPageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        if (user != null) {
            LOGGER.warn("Try opening login page by authored user");
            resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
        } else {
            LOGGER.debug("Try opening login page");
            req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
        }
    }
}
