package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.constant.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.builder.BuilderProvider;
import by.epamtc.courses.entity.builder.CourseBuilder;
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
import java.util.Map;

/**
 * Class implementing editing of course
 *
 * @author DEA
 */
public class EditCourseCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditCourseCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * Course builder instance
     */
    private final CourseBuilder courseBuilder = BuilderProvider.getInstance().getCourseBuilder();

    /**
     * Implementation of 'Edit course' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Try to edit course");

        Map<String, String[]> parameters = req.getParameterMap();
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);

        Map<String, String> validationError = courseService.validateCourse(parameters, locale);

        if (!validationError.isEmpty()) {
            logger.warn("Updating course canceled because course's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            req.getRequestDispatcher(PageName.EDIT_COURSE_PAGE).forward(req, resp);
            return;
        }

        Course course = courseBuilder.createCourseFromParams(parameters);

        try {
            courseService.update(course);

            logger.debug("Updating course successful");

            String courseDetailsURL = PageName.COURSE_DETAILS_URL + course.getId();
            resp.sendRedirect(courseDetailsURL + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException e) {
            logger.error("Updating course error" + e.getMessage(), e);

            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR,
                    resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.EDIT_COURSE_PAGE).forward(req, resp);
        }
    }
}
