package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.CourseResult;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.CourseResultService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CourseMarkPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseMarkPageCommand.class);

    private CourseResultService courseResultService = ServiceProvider.getInstance().getCourseResultService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try set course mark page");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String studentIdStr = req.getParameter(ParameterName.USER_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int studentId = Integer.parseInt(studentIdStr);

            CourseResult courseResult = courseResultService.getCourseResultForUserByCourse(studentId, courseId);

            req.setAttribute(ParameterName.USER_ID, studentId);
            req.setAttribute(ParameterName.COURSE_ID, courseId);
            req.setAttribute(ParameterName.COURSE_RESULT, courseResult);

            req.getRequestDispatcher(PageName.COURSE_MARK_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error while get course mark for user");

            resp.sendRedirect(PageName.MAIN_SERVLET_URL + URLConstant.START_PARAMETERS_SYMBOL +
                    ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE +
                    URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.COURSE_ID + URLConstant.KEY_VALUE_SEPARATOR + courseIdStr +
                    URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + false);
        }
    }
}