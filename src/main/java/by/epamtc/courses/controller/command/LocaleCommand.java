package by.epamtc.courses.controller.command;

import by.epamtc.courses.entity.ParameterName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LocaleCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String locale = req.getParameter(ParameterName.LOCALE);
        HttpSession session = req.getSession();
        session.setAttribute(ParameterName.LOCALE, locale);

        LOGGER.debug("Locale is changed to " + locale);

        doPreviousCommand(req, resp, session);
    }

    private void doPreviousCommand(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        String previousCommand = (String) session.getAttribute(ParameterName.PREVIOUS_COMMAND);

        LOGGER.debug("Start do previous command: " + previousCommand);

        CommandProvider provider = new CommandProvider();
        provider.getCommand(previousCommand).execute(req, resp);
    }
}
