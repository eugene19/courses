package by.epamtc.courses.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface for implementation command
 *
 * @author DEA
 */
public interface Command {

    /**
     * Implementation of command action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}
