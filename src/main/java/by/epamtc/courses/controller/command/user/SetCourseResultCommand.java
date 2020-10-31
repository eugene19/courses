package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.CourseResultService;
import by.epamtc.courses.service.PageName;
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
import java.util.Map;

/**
 * Class implementing add result of course (mark) to student
 *
 * @author DEA
 */
public class SetCourseResultCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SetCourseResultCommand.class);

    /**
     * CourseResult service instance
     */
    private final CourseResultService courseResultService = ServiceProvider.getInstance().getCourseResultService();

    /**
     * Implementation of 'Set course result to student' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try set course result");

        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        Map<String, String[]> parameters = req.getParameterMap();
        Map<String, String> validationError = courseResultService.validateCourseResult(parameters, locale);

        if (!validationError.isEmpty()) {
            LOGGER.warn("Set course result is canceled because course result's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            req.getRequestDispatcher(PageName.COURSE_MARK_PAGE).forward(req, resp);
            return;
        }

        String studentIdStr = req.getParameter(ParameterName.USER_ID);
        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String markStr = req.getParameter(ParameterName.MARK);
        String comment = req.getParameter(ParameterName.COMMENT);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int studentId = Integer.parseInt(studentIdStr);
            int mark = Integer.parseInt(markStr);

            courseResultService.setCourseResult(studentId, courseId, mark, comment);

            LOGGER.debug("Setting course result is success");

            String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
            resp.sendRedirect(courseDetailsURL + URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Error while set course result", e);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.COURSE_MARK_PAGE).forward(req, resp);
        }
    }
}




