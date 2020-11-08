package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.service.CourseResultService;
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
 * Class implementing finish course
 *
 * @author DEA
 */
public class FinishCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FinishCourseCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * CourseResult service instance
     */
    private final CourseResultService courseResultService = ServiceProvider.getInstance().getCourseResultService();

    /**
     * Implementation of 'Finish course' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to finish course");

        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;

        try {
            int courseId = Integer.parseInt(courseIdStr);
            boolean allStudentHasResult = courseResultService.areAllStudentsHaveResult(courseId);

            if (!allStudentHasResult) {
                req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.ERROR_SET_RESULTS));
                req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
                return;
            }

            courseService.updateStatus(courseId, CourseStatus.FINISHED);

            LOGGER.debug("Finishing course successful");

            resp.sendRedirect(courseDetailsURL);
        } catch (ServiceException | IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Finishing course error", e);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
