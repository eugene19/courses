package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Class implementing updating status of student on course
 *
 * @author DEA
 */
public class UpdateUserCourseStatusCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserCourseStatusCommand.class);

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of 'Update student's status' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Trying to change user status on course");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
        String userIdString = req.getParameter(ParameterName.USER_ID);
        String status = req.getParameter(ParameterName.USER_COURSE_STATUS);

        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int userId = Integer.parseInt(userIdString);

            boolean isUpdateOk = userService.updateUserCourseStatus(userId, courseId, UserCourseStatus.valueOf(status));

            if (!isUpdateOk) {
                LOGGER.warn("Updating user course status failed because limit of students");

                req.setAttribute(ParameterName.ERROR,
                        resourceManager.getValue(LocaleMessage.ERROR_UPDATE_STATUS_STUDENTS_LIMIT));
                req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
                return;
            }

            LOGGER.debug("User status is changed");

            resp.sendRedirect(courseDetailsURL);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Error while change user status on course", e);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
