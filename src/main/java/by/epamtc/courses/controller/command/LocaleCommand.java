package by.epamtc.courses.controller.command;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.constant.URLConstant;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing change locale
 *
 * @author DEA
 */
public class LocaleCommand implements Command {
    private static final Logger logger = Logger.getLogger(LocaleCommand.class);

    /**
     * Field containing charset of parameters in url
     */
    private static final String PARAM_VALUES_CHARSET = "UTF-8";

    /**
     * Implementation of 'Change locale' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException if an I/O related error has occurred during the processing
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter(ParameterName.LOCALE);
        HttpSession session = req.getSession();
        session.setAttribute(ParameterName.LOCALE, new Locale(lang));

        logger.debug("Locale is changed to " + lang);

        doPreviousCommand(resp, session);
    }

    /**
     * Method to do previous client's command after changing locale
     *
     * @param resp    the <code>HttpServletResponse</code> object contains response to client
     * @param session the session of client tot get user's previous command
     * @throws IOException if an I/O related error has occurred during the processing
     */
    private void doPreviousCommand(HttpServletResponse resp, HttpSession session) throws IOException {
        logger.debug("Start do previous command");

        //noinspection unchecked
        Map<String, String[]> previousParams =
                (Map<String, String[]>) session.getAttribute(ParameterName.PREVIOUS_COMMAND);

        StringBuilder previousRequest = new StringBuilder(PageName.MAIN_SERVLET_URL);
        int paramCounter = 0;

        for (Map.Entry<String, String[]> parameterPair : previousParams.entrySet()) {
            if (paramCounter == 0) {
                previousRequest.append(URLConstant.START_PARAMETERS);
            }
            if (paramCounter++ != 0) {
                previousRequest.append(URLConstant.PARAMETERS_SEPARATOR);
            }

            // Encoding need if param's value contain cyrillic symbols
            previousRequest
                    .append(parameterPair.getKey())
                    .append(URLConstant.KEY_VALUE_SEPARATOR)
                    .append(URLEncoder.encode(parameterPair.getValue()[0], PARAM_VALUES_CHARSET));
        }

        resp.sendRedirect(previousRequest.toString());
    }
}
