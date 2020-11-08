package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserRole;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Class implementing action of opening 'About user' page
 *
 * @author DEA
 */
public class AboutUserPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AboutUserPageCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of action to open 'About user' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Open about user page");

        String userIdStr = req.getParameter(ParameterName.USER_ID);

        try {
            int userId = Integer.parseInt(userIdStr);
            User foundUser = userService.findUserById(userId);
            req.setAttribute(ParameterName.FOUND_USER, foundUser);

            if (foundUser.getRole() == UserRole.STUDENT) {
                Map<Course, CourseResult> coursesWithResults = courseService.findCoursesWithResultForStudent(userId);
                req.setAttribute(ParameterName.COURSES_WITH_RESULTS, coursesWithResults);
            }

            req.getRequestDispatcher(PageName.ABOUT_USER_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            throw new ServletException("Error while opening about user page", e);
        }
    }
}
