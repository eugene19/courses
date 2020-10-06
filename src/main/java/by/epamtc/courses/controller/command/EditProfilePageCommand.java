package by.epamtc.courses.controller.command;

import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProfilePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditProfilePageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening edit profile page");
        req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
    }
}
