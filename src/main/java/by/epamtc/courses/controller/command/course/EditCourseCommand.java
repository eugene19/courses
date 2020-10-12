package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.builder.CourseBuilder;
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
import java.util.Locale;
import java.util.Map;

public class EditCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCourseCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();
    private CourseBuilder courseBuilder = new CourseBuilder();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to edit course");
        String page;

        Map<String, String[]> parameters = req.getParameterMap();
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);

        ResourceManager resourceManager = new ResourceManager(locale);
        Map<String, String> validationError = courseService.validateCourse(parameters, locale);

        if (validationError.isEmpty()) {
            Course course = courseBuilder.createCourseFromParams(parameters);

            try {
                courseService.update(course);

                LOGGER.debug("Updating course successful");

                // TODO: 10/9/20 Заменить редирект не на список курсов, а на детали курса
                //  и добавить там сообщение об успехе
                resp.sendRedirect(PageName.MAIN_SERVLET_URL
                        + URLConstant.START_PARAMETERS_SYMBOL
                        + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSES_PAGE
                        + URLConstant.PARAMETERS_SEPARATOR
                        + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
                return;
            } catch (ServiceException e) {
                LOGGER.error("Updating course error" + e.getMessage(), e);

                req.setAttribute(ParameterName.INIT, parameters);
                req.setAttribute(ParameterName.ERROR,
                        resourceManager.getValue(LocaleMessage.ERROR_PAGE_MESSAGE));
                page = PageName.EDIT_COURSE_PAGE;
            }
        } else {
            LOGGER.warn("Updating course canceled because course's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            page = PageName.EDIT_COURSE_PAGE;
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}
