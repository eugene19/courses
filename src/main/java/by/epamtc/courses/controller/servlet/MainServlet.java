package by.epamtc.courses.controller.servlet;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandProvider;
import by.epamtc.courses.entity.ParameterName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 8717762438987053788L;
    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

    private CommandProvider provider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        Command command = provider.getCommand(req.getParameter(ParameterName.COMMAND));
        try {
            command.execute(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
            try {
                resp.sendError(500);
            } catch (IOException ex) {
                LOGGER.error("Error while sending error.", e);
            }
        }
    }
}
