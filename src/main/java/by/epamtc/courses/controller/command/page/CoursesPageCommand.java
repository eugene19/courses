package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Class implementing action of opening 'Courses list' page
 *
 * @author DEA
 */
public class CoursesPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CoursesPageCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * Implementation of action to open 'Courses list' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening courses page");

        HttpSession session = req.getSession();
        String[] statuses = (String[]) session.getAttribute(ParameterName.STATUS);
        String sort = (String) session.getAttribute(ParameterName.SORT);
        String pageStr = req.getParameter(ParameterName.PAGE);

        try {
            int page = (pageStr == null) ? 0 : Integer.parseInt(pageStr);
            List<Course> courses = courseService.findCoursesWithStatusForPage(statuses, page, sort);
            req.setAttribute(ParameterName.COURSE_LIST, courses);

            int coursesCount = courseService.countCoursesInStatus(statuses);
            req.setAttribute(ParameterName.COURSES_COUNT, coursesCount);
        } catch (ServiceException e) {
            LOGGER.error("Error when get courses for page " + pageStr, e);

            Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
        }

        req.getRequestDispatcher(PageName.COURSES_PAGE).forward(req, resp);
    }
}
