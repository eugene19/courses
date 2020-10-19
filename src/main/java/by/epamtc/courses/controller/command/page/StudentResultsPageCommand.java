package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class StudentResultsPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(StudentResultsPageCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Open student results page");

        User user = (User) req.getSession().getAttribute(ParameterName.USER);

        try {
            Map<Course, CourseResult> coursesWithResults = courseService.takeCoursesWithResultForStudent(user.getId());
            req.setAttribute(ParameterName.COURSES_WITH_RESULTS, coursesWithResults);
            req.getRequestDispatcher(PageName.STUDENT_RESULTS).forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException("Error while take courses with results for student");
        }
    }
}
