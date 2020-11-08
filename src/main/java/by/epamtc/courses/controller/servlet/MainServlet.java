package by.epamtc.courses.controller.servlet;

import by.epamtc.courses.constant.ErrorCode;
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
     * @throws IOException      if an input or output error is detected when
     *                          the servlet handles the GET request
     * @throws ServletException if the request for the GET
     *                          could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
     * @throws IOException      if an input or output error is detected
     *                          when the servlet handles the request
     * @throws ServletException if the request for the POST
     *                          could not be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Take command from request and execute this command
     *
     * @param req  an {@link HttpServletRequest} object that contains the
     *             request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the
     *             response the servlet sends to the client
     * @throws IOException      if an input or output error is detected
     *                          when the servlet handles the request
     * @throws ServletException if the request could not be handled
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String commandName = req.getParameter(ParameterName.COMMAND);

        try {
            Command command = provider.getCommand(commandName);
            command.execute(req, resp);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Such command is not found");
            resp.sendError(ErrorCode.NOT_FOUND);
        }
    }
}
