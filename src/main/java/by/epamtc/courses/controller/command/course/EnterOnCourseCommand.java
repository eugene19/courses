package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EnterOnCourseCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EnterOnCourseCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("User try to enter on course");

        String courseIdString = req.getParameter(ParameterName.COURSE_ID);
        User user = (User) req.getSession().getAttribute(ParameterName.USER);

        try {
            int courseId = Integer.parseInt(courseIdString);
            courseService.enterUserOnCourse(user.getId(), courseId);

            sendRedirectWithStatus(true, courseIdString, resp);
        } catch (NumberFormatException e) {
            throw new ServletException("Trying to enter on course is canceled because 'course id' is not 'integer'");
        } catch (ServiceException e) {
            LOGGER.error("Error while entering on course", e);
            sendRedirectWithStatus(false, courseIdString, resp);
        }
    }

    private void sendRedirectWithStatus(boolean statusIsOk, String courseId, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(PageName.MAIN_SERVLET_URL + URLConstant.START_PARAMETERS_SYMBOL +
                ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSE_DETAILS_PAGE +
                URLConstant.PARAMETERS_SEPARATOR +
                ParameterName.COURSE_ID + URLConstant.KEY_VALUE_SEPARATOR + courseId +
                URLConstant.PARAMETERS_SEPARATOR +
                ParameterName.IS_SENT_OK + URLConstant.KEY_VALUE_SEPARATOR + statusIsOk);
    }
}
