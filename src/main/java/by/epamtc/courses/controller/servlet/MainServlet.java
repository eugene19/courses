package by.epamtc.courses.controller.servlet;

import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class for processing requests from client
 *
 * @author DEA
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 8717762438987053788L;
    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

    /**
     * Command provider instance
     */
    private final CommandProvider provider = new CommandProvider();

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a GET request
     *
     * @param req  an {@link HttpServletRequest} object that contains the
     *             request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the
     *             response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a POST request
     *
     * @param req  an {@link HttpServletRequest} object that contains the
     *             request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the
     *             response the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    /**
     * Take command from request and execute this command
     *
     * @param req  an {@link HttpServletRequest} object that contains the
     *             request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the
     *             response the servlet sends to the client
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) {
        Command command = provider.getCommand(req.getParameter(ParameterName.COMMAND));
        try {
            command.execute(req, resp);
        } catch (IOException | ServletException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            try {
                resp.sendError(500);
            } catch (IOException ex) {
                LOGGER.error("Error while sending error.", e);
            }
        }
    }
}
