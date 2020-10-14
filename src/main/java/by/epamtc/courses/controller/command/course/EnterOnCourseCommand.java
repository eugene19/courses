package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
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

public class EnterOnCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EnterOnCourseCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("User try to apply on course");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
        User user = (User) req.getSession().getAttribute(ParameterName.USER);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            courseService.enterUserOnCourse(user.getId(), courseId);

            LOGGER.debug("User applied on course successful");

            resp.sendRedirect(courseDetailsURL);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Error while applied on course", e);

            Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
