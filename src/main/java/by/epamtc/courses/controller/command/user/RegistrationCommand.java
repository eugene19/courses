package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.builder.UserBuilder;
import by.epamtc.courses.service.PageName;
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

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private UserService userService = ServiceProvider.getInstance().getUserService();
    private UserBuilder userBuilder = new UserBuilder();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try register user");
        String page;

        Map<String, String[]> parameters = req.getParameterMap();
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);

        ResourceManager resourceManager = new ResourceManager(locale);
        Map<String, String> validationError = userService.validateUserRegistrationData(parameters, locale);

        if (validationError.isEmpty()) {
            UserAuthData user = userBuilder.createUserDataFromParams(parameters);

            try {
                userService.register(user);

                LOGGER.debug("Registration successful " + user.getLogin());

                resp.sendRedirect(PageName.MAIN_SERVLET_URL
                        + URLConstant.START_PARAMETERS_SYMBOL
                        + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_LOGIN_PAGE
                        + URLConstant.PARAMETERS_SEPARATOR
                        + ParameterName.IS_REGISTRATION_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
                return;
            } catch (ServiceException e) {
                LOGGER.error("Registration error" + e.getMessage(), e);

                req.setAttribute(ParameterName.INIT, parameters);
                req.setAttribute(ParameterName.ERROR,
                        resourceManager.getValue(LocaleMessage.ERROR_PAGE_MESSAGE));
                page = PageName.REGISTRATION_PAGE;
            }
        } else {
            LOGGER.warn("Registration canceled because user's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            page = PageName.REGISTRATION_PAGE;
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}
