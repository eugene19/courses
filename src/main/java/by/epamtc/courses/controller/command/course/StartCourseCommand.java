package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.CourseStatus;
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
import java.io.IOException;
import java.util.Locale;

public class StartCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(StartCourseCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to start course");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;

        try {
            int courseId = Integer.parseInt(courseIdStr);

            courseService.updateStatus(courseId, CourseStatus.IN_PROGRESS);

            LOGGER.debug("Starting course successful");

            resp.sendRedirect(courseDetailsURL);
        } catch (ServiceException | IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Starting course error", e);

            Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
