package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.Course;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.entity.UserCourseStatus;
import by.epamtc.courses.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Class implementing action of opening 'Course details' page
 *
 * @author DEA
 */
public class CourseDetailsPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseDetailsPageCommand.class);

    /**
     * Course service instance
     */
    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    /**
     * User service instance
     */
    private UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of action to open 'Course details' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening course detail page");

        User user = (User) req.getSession().getAttribute(ParameterName.USER);
        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.findCourseById(courseId);

            if (user != null) {
                UserCourseStatus userCourseStatus = courseService.takeUserCourseStatus(user.getId(), courseId);
                req.setAttribute(ParameterName.USER_COURSE_STATUS, userCourseStatus);

                Map<User, UserCourseStatus> studentsOfCourse = userService.findAllStudentsOnCourse(courseId);
                req.setAttribute(ParameterName.USERS_ON_COURSE, studentsOfCourse);
            }

            req.setAttribute(ParameterName.COURSE, course);

            User lecturer = userService.findUserById(course.getLecturerId());
            req.setAttribute(ParameterName.LECTURER, lecturer);

            int countEnteredUsers = userService.countStudentsOnCourseInStatus(courseId, UserCourseStatus.ENTERED);
            req.setAttribute(ParameterName.COUNT_ENTERED_USERS, countEnteredUsers);

            req.getRequestDispatcher(PageName.COURSE_DETAILS_PAGE).forward(req, resp);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Error while getting course with id " + courseIdStr);
        }
    }
}
