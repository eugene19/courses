package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
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
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class UploadPhotoCommand implements Command {
    public static final String SAVE_DIRECTORY = "uploadFiles";
    private static final Logger LOGGER = Logger.getLogger(UploadPhotoCommand.class);
    private static final String EMPTY_STRING = "";
    private static final String BACK_SLASH_SYMBOL = "\\";

    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try upload user photo");

        HttpSession session = req.getSession();
        ResourceManager resourceManager = new ResourceManager((Locale) session.getAttribute(ParameterName.LOCALE));
        User user = (User) session.getAttribute(ParameterName.USER);

        try {
            String fullSavePath = pathToSavePhoto(req);
            createDirIfNoExist(fullSavePath);

            Part file = req.getPart(ParameterName.PHOTO_FILE);
            String fileName = file.getSubmittedFileName();

            if (fileName == null || fileName.isEmpty()) {
                LOGGER.warn("Uploading user photo canceled because file name is incorrect: " + fileName);
                String errorMessage = resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_FILE_NAME);
                sendErrorUploading(req, resp, errorMessage);
                return;
            }

            file.write(fullSavePath + File.separator + fileName);

            user.setPhotoPath(fileName);
            userService.update(user);

            sendSuccessUploading(resp);
        } catch (ServiceException | IOException e) {
            LOGGER.warn("Uploading user photo canceled because user's data is invalid");
            String errorMessage = resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG);
            sendErrorUploading(req, resp, errorMessage);
        }
    }

    private String pathToSavePhoto(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        String appPath;
        appPath = req.getServletContext().getRealPath(EMPTY_STRING);
        appPath = appPath.replace(BACK_SLASH_SYMBOL, File.separator);

        if (!appPath.endsWith(File.separator)) {
            appPath += File.separator;
        }

        return appPath + SAVE_DIRECTORY + File.separator + user.getId();
    }

    private void createDirIfNoExist(String path) {
        File fileSaveDir = new File(path);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    private void sendSuccessUploading(HttpServletResponse resp) throws IOException {
        resp.sendRedirect(PageName.MAIN_SERVLET_URL
                + URLConstant.START_PARAMETERS_SYMBOL
                + ParameterName.COMMAND + URLConstant.KEY_VALUE_SEPARATOR + CommandName.GET_PROFILE_PAGE
                + URLConstant.PARAMETERS_SEPARATOR
                + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
    }

    private void sendErrorUploading(HttpServletRequest req, HttpServletResponse resp, String errorMessage) throws ServletException, IOException {
        req.setAttribute(ParameterName.ERROR, errorMessage);
        req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
    }
}