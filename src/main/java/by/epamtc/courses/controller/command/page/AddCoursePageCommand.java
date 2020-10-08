package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCoursePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddCoursePageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening add new course page");
        req.getRequestDispatcher(PageName.ADD_COURSE_PAGE).forward(req, resp);
    }
}
