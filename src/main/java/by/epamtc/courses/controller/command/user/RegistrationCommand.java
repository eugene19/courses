package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.constant.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.builder.BuilderProvider;
import by.epamtc.courses.entity.builder.UserBuilder;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing registration of client
 *
 * @author DEA
 */
public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * User builder instance
     */
    private final UserBuilder userBuilder = BuilderProvider.getInstance().getUserBuilder();

    /**
     * Implementation of 'Registration' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Try register user");

        Map<String, String[]> parameters = req.getParameterMap();
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        Map<String, String> validationError = userService.validateUserRegistrationData(parameters, locale);
        if (!validationError.isEmpty()) {
            logger.warn("Registration canceled because user's data is invalid");
            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            req.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(req, resp);
            return;
        }

        UserAuthData user = userBuilder.createUserDataFromParams(parameters);

        try {
            userService.register(user);

            logger.debug("Registration successful " + user.getLogin());
            resp.sendRedirect(PageName.MAIN_SERVLET_URL
                    + URLConstant.START_PARAMETERS
                    + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_LOGIN_PAGE
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_REGISTRATION_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
            return;
        } catch (ServiceException e) {
            logger.error("Registration error", e);
            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
        }

        req.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(req, resp);
    }
}
