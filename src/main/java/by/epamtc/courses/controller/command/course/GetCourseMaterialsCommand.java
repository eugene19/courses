package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.User;
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
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing getting course's materials
 *
 * @author DEA
 */
public class GetCourseMaterialsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetCourseMaterialsCommand.class);

    /**
     * Field containing code of status http 'permission denied' error response
     */
    private static final int ERROR_PERMISSION_DENIED = 403;

    /**
     * Field containing charset of parameters in url
     */
    private static final String PARAM_VALUES_CHARSET = "UTF-8";

    /**
     * Course service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of 'Get course's materials' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to get courses materials");

        User user = (User) req.getSession().getAttribute(ParameterName.USER);

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String lecturerIdStr = req.getParameter(ParameterName.LECTURER_ID);
        String materialPath = req.getParameter(ParameterName.MATERIALS);

        String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        if (materialPath == null || materialPath.isEmpty()) {
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.NO_MATERIALS));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int lecturerId = Integer.parseInt(lecturerIdStr);

            Map<User, UserCourseStatus> usersOnCourse = userService.findAllStudentsOnCourse(courseId);
            if (!usersOnCourse.containsKey(user) && user.getId() != lecturerId) {
                resp.sendError(ERROR_PERMISSION_DENIED);
                return;
            }

            LOGGER.debug("Course materials are ");

            String urlToMaterials = String.format(PageName.UPLOAD_FILES_FORMAT_URL, lecturerId, URLEncoder.encode(materialPath, PARAM_VALUES_CHARSET));
            resp.sendRedirect(urlToMaterials);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.error("Error while getting course materials", e);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
