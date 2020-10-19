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

public class CourseDetailsPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CourseDetailsPageCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();
    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening course detail page");

        User user = (User) req.getSession().getAttribute(ParameterName.USER);
        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.getCourse(courseId);

            if (user != null) {
                UserCourseStatus userCourseStatus = courseService.getUserCourseStatus(user.getId(), courseId);
                req.setAttribute(ParameterName.USER_COURSE_STATUS, userCourseStatus);

                Map<User, UserCourseStatus> studentsOfCourse = userService.takeUsersOnCourse(courseId);
                req.setAttribute(ParameterName.USERS_ON_COURSE, studentsOfCourse);
            }

            req.setAttribute(ParameterName.COURSE, course);

            User lecturer = userService.getUserById(course.getLecturerId());
            req.setAttribute(ParameterName.LECTURER, lecturer);

            int countEnteredUsers = userService.countEnteredUsersOnCourse(courseId);
            req.setAttribute(ParameterName.COUNT_ENTERED_USERS, countEnteredUsers);

            req.getRequestDispatcher(PageName.COURSE_DETAILS_PAGE).forward(req, resp);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Error while getting course with id " + courseIdStr);
        }
    }
}
