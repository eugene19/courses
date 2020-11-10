package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class implementing action of opening 'Edit profile' page
 *
 * @author DEA
 */
public class EditProfilePageCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditProfilePageCommand.class);

    /**
     * Implementation of action to open 'Edit profile' page
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Opening edit profile page");
        req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
    }
}
