package by.epamtc.courses.controller.command;

import by.epamtc.courses.entity.UserAuthData;
import by.epamtc.courses.entity.builder.UserBuilder;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.validation.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String INIT_ATTRIBUTE = "init";
    private static final String ERRORS_ATTRIBUTE = "errors";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String MESSAGE_ATTRIBUTE = "message";

    private UserService userService = ServiceProvider.getInstance().getUserService();
    private UserBuilder userBuilder = new UserBuilder();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try register user.");
        String page;

        Map<String, String[]> parameters = req.getParameterMap();
        String lang = (String) req.getSession().getAttribute(LOCALE_ATTRIBUTE);
        UserValidator userValidator = new UserValidator(parameters, lang);

        if (userValidator.validateAll().isValid()) {
            UserAuthData user = userBuilder.createUserDataFromParams(parameters);

            try {
                userService.register(user);

                LOGGER.debug("Registration successful " + user.getLogin());
                req.setAttribute(MESSAGE_ATTRIBUTE, "Registration successful.");
                page = PageName.LOGIN_PAGE;
            } catch (ServiceException e) {
                LOGGER.error("Registration error" + e.getMessage(), e);

                req.setAttribute(INIT_ATTRIBUTE, parameters);
                req.setAttribute(ERROR_ATTRIBUTE, "Registration error, try later.");
                page = PageName.REGISTRATION_PAGE;
            }
        } else {
            LOGGER.warn("Registration canceled because user's data is invalid.");

            Map<String, String> errors = userValidator.getErrors();
            req.setAttribute(INIT_ATTRIBUTE, parameters);
            req.setAttribute(ERRORS_ATTRIBUTE, errors);
            page = PageName.REGISTRATION_PAGE;
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}
