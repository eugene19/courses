package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("User logout");
        req.getSession().removeAttribute(ParameterName.USER);
        resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
    }
}
