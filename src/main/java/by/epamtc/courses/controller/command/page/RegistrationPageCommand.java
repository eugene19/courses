package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class implementing action of opening 'Registration' page
 *
 * @author DEA
 */
public class RegistrationPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationPageCommand.class);

    /**
     * Implementation of action to open 'Registration' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Opening registration page");
        req.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(req, resp);
    }
}
