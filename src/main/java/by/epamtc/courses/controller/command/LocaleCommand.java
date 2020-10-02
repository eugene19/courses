package by.epamtc.courses.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LocaleCommand.class);

    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String locale = req.getParameter(LOCALE_ATTRIBUTE);
        HttpSession session = req.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);

        LOGGER.debug("Locale is changed to " + locale);

        doPreviousCommand(req, resp, session);
    }

    private void doPreviousCommand(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        String previousCommand = (String) session.getAttribute("previousCommand");

        LOGGER.debug("Start do previous command: " + previousCommand);

        CommandProvider provider = new CommandProvider();
        provider.getCommand(previousCommand).execute(req, resp);
//        String referer = req.getHeader("Referer");
//        resp.sendRedirect(referer + "?command=" + previousCommand);
    }
}
