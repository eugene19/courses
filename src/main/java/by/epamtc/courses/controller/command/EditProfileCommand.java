package by.epamtc.courses.controller.command;

import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class EditProfileCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditProfileCommand.class);

    private UserService userService = ServiceProvider.getInstance().getUserService();
    private UserBuilder userBuilder = new UserBuilder();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to edit user's profile");

        String page;

        Map<String, String[]> parameters = req.getParameterMap();
        Locale locale = (Locale) req.getSession().getAttribute(ParameterName.LOCALE);

        ResourceManager resourceManager = new ResourceManager(locale);
        Map<String, String> validationError = userService.validateUserProfileData(parameters, locale);

        if (validationError.isEmpty()) {
            User user = userBuilder.createUserFromParams(parameters);


            try {
                userService.update(user);

                HttpSession session = req.getSession();
                session.setAttribute(ParameterName.USER, user);

                LOGGER.debug("Updating user successful");

                resp.sendRedirect("/main?"
                        + ParameterName.COMMAND + "=" + CommandName.GET_PROFILE_PAGE +
                        "&" + ParameterName.IS_UPDATING_OK + "=" + true);
                return;
            } catch (ServiceException e) {
                LOGGER.error("Updating user error" + e.getMessage(), e);

                req.setAttribute(ParameterName.INIT, parameters);
                req.setAttribute(ParameterName.ERROR,
                        resourceManager.getValue(LocaleMessage.ERROR_PAGE_MESSAGE));
                page = PageName.EDIT_PROFILE_PAGE;
            }
        } else {
            LOGGER.warn("Updating user canceled because user's data is invalid");

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            page = PageName.EDIT_PROFILE_PAGE;
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}
