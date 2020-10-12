package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserCourseStatusCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserCourseStatusCommand.class);

    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Trying to change user status on course");

        String courseIdString = req.getParameter(ParameterName.COURSE_ID);
        String userIdString = req.getParameter(ParameterName.USER_ID);
        String status = req.getParameter(ParameterName.USER_COURSE_STATUS);

        try {
            int courseId = Integer.parseInt(courseIdString);
            int userId = Integer.parseInt(userIdString);

            userService.updateUserCourseStatus(userId, courseId, UserCourseStatus.valueOf(status));

            sendRedirectWithStatus(true, courseIdString, resp);
        } catch (NumberFormatException e) {
            throw new ServletException("Trying to change user status on course is canceled because 'course id' or 'user id' is not 'integer'");
        } catch (ServiceException e) {
            LOGGER.error("Error while change user status on course", e);
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
