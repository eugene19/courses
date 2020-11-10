package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Class implementing start course
 *
 * @author DEA
 */
public class StartCourseCommand implements Command {
    private static final Logger logger = Logger.getLogger(StartCourseCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * Implementation of 'Start course' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Try to start course");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;

        try {
            int courseId = Integer.parseInt(courseIdStr);

            courseService.updateStatus(courseId, CourseStatus.IN_PROGRESS);

            logger.debug("Starting course successful");

            resp.sendRedirect(courseDetailsURL);
        } catch (ServiceException | IllegalArgumentException | NullPointerException e) {
            logger.error("Starting course error", e);

            Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
