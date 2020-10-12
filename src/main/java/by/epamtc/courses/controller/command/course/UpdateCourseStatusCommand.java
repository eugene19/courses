package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.CourseStatus;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCourseStatusCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UpdateCourseStatusCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to change course status");

        String status = req.getParameter(ParameterName.STATUS);
        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);

        try {
            CourseStatus courseStatus = CourseStatus.valueOf(status);
            int courseId = Integer.parseInt(courseIdStr);

            courseService.updateStatus(courseId, courseStatus);

            LOGGER.debug("Updating course status successful");

            resp.sendRedirect(PageName.MAIN_SERVLET_URL
                    + URLConstant.START_PARAMETERS_SYMBOL
                    + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException | IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Updating course status error", e);
            resp.sendRedirect(PageName.MAIN_SERVLET_URL
                    + URLConstant.START_PARAMETERS_SYMBOL
                    + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + false);
        }
    }
}
