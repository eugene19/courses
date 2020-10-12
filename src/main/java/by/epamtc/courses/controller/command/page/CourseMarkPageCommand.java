package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CourseMarkPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseMarkPageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try set course mark page");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String studentIdStr = req.getParameter(ParameterName.USER_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int studentId = Integer.parseInt(studentIdStr);

            req.setAttribute(ParameterName.USER_ID, studentId);
            req.setAttribute(ParameterName.COURSE_ID, courseId);

            req.getRequestDispatcher(PageName.COURSE_MARK_PAGE).forward(req, resp);
        } catch (NumberFormatException ex) {
            LOGGER.error("Error while get user by id", ex);

            // TODO: 10/12/20 Обработать
            throw new ServletException(ex);
        }
    }
}