package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class implementing action of 'Filter courses list' page
 *
 * @author DEA
 */
public class FilterCoursesCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FilterCoursesCommand.class);

    /**
     * Save in session values of filter for courses list
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to set course filter and sorting");

        String[] statuses = req.getParameterValues(ParameterName.STATUS);
        String sort = req.getParameter(ParameterName.SORT);

        req.getSession().setAttribute(ParameterName.STATUS, statuses);
        req.getSession().setAttribute(ParameterName.SORT, sort);

        resp.sendRedirect(PageName.COURSES_URL);
    }
}
