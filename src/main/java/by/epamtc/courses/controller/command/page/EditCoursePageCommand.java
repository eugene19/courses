package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class EditCoursePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditCoursePageCommand.class);

    private static final int ERROR_PERMISSION_DENIED = 403;

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening edit course page");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.getCourse(courseId);

            if (course.getLecturerId() != user.getId()) {
                resp.sendError(ERROR_PERMISSION_DENIED);
                return;
            }

            req.setAttribute(ParameterName.COURSE, course);
            req.getRequestDispatcher(PageName.EDIT_COURSE_PAGE).forward(req, resp);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Error while getting course with id " + courseIdStr, e);

            String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
            Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
