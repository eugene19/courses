package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCoursePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCoursePageCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening edit course page");

        String courseId = req.getParameter(ParameterName.COURSE_ID);

        if (courseId == null) {
            throw new ServletException("Opening of editing page is canceled because 'course id' is 'null'");
        }

        try {
            int id = Integer.parseInt(courseId);
            Course course = courseService.getCourse(id);

            req.setAttribute(ParameterName.COURSE, course);
            req.getRequestDispatcher(PageName.EDIT_COURSE_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException("Error while getting course with id " + courseId);
        } catch (NumberFormatException ex) {
            throw new ServletException("Opening of editing page is canceled because 'course id' is not 'integer'");
        }
    }
}
