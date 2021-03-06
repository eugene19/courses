package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.constant.ErrorCode;
import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.CourseService;
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

/**
 * Class implementing action of opening 'Edit course' page
 *
 * @author DEA
 */
public class EditCoursePageCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditCoursePageCommand.class);

    /**
     * Course service instance
     */
    private final CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * Implementation of action to open 'Edit course' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Opening edit course page");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.findCourseById(courseId);

            if (course.getLecturerId() != user.getId()) {
                resp.sendError(ErrorCode.PERMISSION_DENIED);
                return;
            }

            req.setAttribute(ParameterName.COURSE, course);
            req.getRequestDispatcher(PageName.EDIT_COURSE_PAGE).forward(req, resp);
        } catch (NumberFormatException | ServiceException e) {
            logger.error("Error while getting course with id " + courseIdStr, e);

            String courseDetailsURL = PageName.COURSE_DETAILS_URL + courseIdStr;
            Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(courseDetailsURL).forward(req, resp);
        }
    }
}
