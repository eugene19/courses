package by.epamtc.courses.controller.servlet;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 8717762438987053788L;
    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

    private static final String COMMAND_PARAM = "command";

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
        Command command = provider.getCommand(req.getParameter(COMMAND_PARAM));
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
