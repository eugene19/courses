package by.epamtc.courses.controller.command;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LocaleCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LocaleCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter(ParameterName.LOCALE);
        HttpSession session = req.getSession();
        session.setAttribute(ParameterName.LOCALE, new Locale(lang));

        LOGGER.debug("Locale is changed to " + lang);

        doPreviousCommand(resp, session);
    }

    private void doPreviousCommand(HttpServletResponse resp, HttpSession session) throws IOException {
        LOGGER.debug("Start do previous command");

        //noinspection unchecked
        Map<String, String[]> previousParams =
                (Map<String, String[]>) session.getAttribute(ParameterName.PREVIOUS_COMMAND);

        StringBuilder previousRequest = new StringBuilder(PageName.MAIN_SERVLET_URL);
        int paramCounter = 0;

        for (Map.Entry<String, String[]> parameterPair : previousParams.entrySet()) {
            if (paramCounter == 0) {
                previousRequest.append(URLConstant.START_PARAMETERS_SYMBOL);
            }
            if (paramCounter++ != 0) {
                previousRequest.append(URLConstant.PARAMETERS_SEPARATOR);
            }

            previousRequest
                    .append(parameterPair.getKey())
                    .append(URLConstant.KEY_VALUE_SEPARATOR)
                    .append(parameterPair.getValue()[0]);
        }

        resp.sendRedirect(previousRequest.toString());
    }
}
