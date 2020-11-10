package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class implementing log out of client
 *
 * @author DEA
 */
public class LogoutCommand implements Command {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    /**
     * Implementation of 'Log out' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException if an I/O related error has occurred during the processing
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("User log out");
        req.getSession().removeAttribute(ParameterName.USER);
        resp.sendRedirect(PageName.DEFAULT_PAGE_URL);
    }
}
