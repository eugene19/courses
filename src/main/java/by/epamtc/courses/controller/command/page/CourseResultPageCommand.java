package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.CourseResult;
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

public class CourseResultPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseResultPageCommand.class);

    private CourseResultService courseResultService = ServiceProvider.getInstance().getCourseResultService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try set course result page");

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