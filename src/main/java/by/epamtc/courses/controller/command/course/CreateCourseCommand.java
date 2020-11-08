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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing adding of course
 *
 * @author DEA
 */
public class CreateCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CreateCourseCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * Course builder instance
     */
    private final CourseBuilder courseBuilder = BuilderProvider.getInstance().getCourseBuilder();

    /**
     * Implementation of 'Add course' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try create course");

        Map<String, String[]> parameters = req.getParameterMap();
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        Map<String, String> validationError = courseService.validateCourse(parameters, locale);

        if (!validationError.isEmpty()) {
            LOGGER.warn("Creation of course canceled because course's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            req.getRequestDispatcher(PageName.ADD_COURSE_PAGE).forward(req, resp);
            return;
        }
        Course course = courseBuilder.createCourseFromParams(parameters);

        try {
            courseService.create(course);

            LOGGER.debug("Creation of course is successful " + course.getSummary());

            resp.sendRedirect(PageName.COURSES_URL
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_CREATION_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException e) {
            LOGGER.error("Creation of course error" + e.getMessage(), e);

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR,
                    resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.ADD_COURSE_PAGE).forward(req, resp);
        }
    }
}
