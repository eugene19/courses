package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceProvider;
import by.epamtc.courses.service.UserService;
import by.epamtc.courses.service.i18n.LocaleMessage;
import by.epamtc.courses.service.i18n.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class UploadPhotoCommand implements Command {
    public static final String SAVE_DIRECTORY = "uploadFiles";
    private static final Logger LOGGER = Logger.getLogger(UploadPhotoCommand.class);
    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        try {
            String appPath = req.getServletContext().getRealPath("");
            appPath = appPath.replace("\\", File.separator);

            String fullSavePath;
            if (!appPath.endsWith(File.separator)) {
                appPath += File.separator;
            }

            fullSavePath = appPath + SAVE_DIRECTORY + File.separator + user.getId();

            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            Part file = req.getPart("file");
            String fileName = file.getSubmittedFileName();
            if (fileName != null && fileName.length() > 0) {
                String filePath = fullSavePath + File.separator + fileName;
                file.write(filePath);
            }

            user.setPhotoPath(fileName);
            userService.update(user);

            resp.sendRedirect("/main?"
                    + ParameterName.COMMAND + "=" + CommandName.GET_PROFILE_PAGE +
                    "&" + ParameterName.IS_UPDATING_OK + "=" + true);
        } catch (Exception e) {
            LOGGER.warn("Updating user canceled because user's data is invalid");
            ResourceManager resourceManager = new ResourceManager((Locale) session.getAttribute(ParameterName.LOCALE));
            req.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
        }
    }
}
