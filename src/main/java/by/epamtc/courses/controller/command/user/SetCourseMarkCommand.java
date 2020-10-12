package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
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

public class SetCourseMarkCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SetCourseMarkCommand.class);

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try set course mark");

        String studentIdStr = req.getParameter(ParameterName.USER_ID);
        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        String mark = req.getParameter(ParameterName.MARK);
        String comment = req.getParameter(ParameterName.COMMENT);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int studentId = Integer.parseInt(studentIdStr);

            courseService.setCourseResult(studentId, courseId, mark, comment);

            resp.sendRedirect(PageName.MAIN_SERVLET_URL + URLConstant.START_PARAMETERS_SYMBOL +
                    ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_COURSES_PAGE +
                    URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException e) {
            LOGGER.error("Error while get user by id", e);
            // TODO: 10/12/20 send error
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            LOGGER.error("Error while get user by id", ex);
            // TODO: 10/12/20 send error
        }
    }
}




