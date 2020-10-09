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
import java.util.List;

public class CoursesPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CoursesPageCommand.class);
    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening courses page");
        try {
            List<Course> courses = courseService.takeAllCourses();
            req.setAttribute(ParameterName.COURSE_LIST, courses);
        } catch (ServiceException e) {
            LOGGER.error("Error when get all courses: " + e.getMessage());
            // todo: localize message below
            req.setAttribute(ParameterName.ERROR, "Error when get all courses");
        }

        req.getRequestDispatcher(PageName.COURSES_PAGE).forward(req, resp);
    }
}