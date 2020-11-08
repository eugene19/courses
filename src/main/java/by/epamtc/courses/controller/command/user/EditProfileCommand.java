package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.constant.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing editing of user's profile
 *
 * @author DEA
 */
public class EditProfileCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditProfileCommand.class);

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * User builder instance
     */
    private final UserBuilder userBuilder = BuilderProvider.getInstance().getUserBuilder();

    /**
     * Implementation of 'Edit user's profile' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try to edit user's profile");

        Map<String, String[]> parameters = req.getParameterMap();
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);

        Map<String, String> validationError = userService.validateUserProfileData(parameters, locale);

        if (!validationError.isEmpty()) {
            LOGGER.warn("Updating user's profile canceled because user's data is invalid");
            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERRORS, validationError);
            req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
            return;
        }

        try {
            User user = userBuilder.createUserFromParams(parameters);
            userService.update(user);
            session.setAttribute(ParameterName.USER, user);

            LOGGER.debug("Updating user successful");

            resp.sendRedirect(PageName.PROFILE_URL
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException e) {
            LOGGER.error("Updating user's profile error", e);

            ResourceManager resourceManager = new ResourceManager(locale);

            req.setAttribute(ParameterName.INIT, parameters);
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
        }
    }
}
