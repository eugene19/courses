package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.service.CourseResultService;
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
 * Class implementing action of opening 'Course result' page
 *
 * @author DEA
 */
public class CourseResultPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseResultPageCommand.class);

    /**
     * CourseResult service instance
     */
    private final CourseResultService courseResultService = ServiceProvider.getInstance().getCourseResultService();

    /**
     * Implementation of action to open 'Course result' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try open course result page");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String studentIdStr = req.getParameter(ParameterName.USER_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int studentId = Integer.parseInt(studentIdStr);

            CourseResult courseResult = courseResultService.takeCourseResultForUser(studentId, courseId);

            req.setAttribute(ParameterName.USER_ID, studentId);
            req.setAttribute(ParameterName.COURSE_ID, courseId);
            req.setAttribute(ParameterName.COURSE_RESULT, courseResult);

            req.getRequestDispatcher(PageName.COURSE_MARK_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error while get course result for user");

            String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
            Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}