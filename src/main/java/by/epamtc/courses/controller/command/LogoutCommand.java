package by.epamtc.courses.controller.command;

import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("Logout user.");
        req.getSession().setAttribute(USER_ATTRIBUTE, null);
        resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
    }
}
