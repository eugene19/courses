package by.epamtc.courses.controller.command.user;

import by.epamtc.courses.constant.PageName;
import by.epamtc.courses.constant.ParameterName;
import by.epamtc.courses.constant.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.User;
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

/**
 * Class implementing add user's photo
 *
 * @author DEA
 */
public class UploadPhotoCommand implements Command {
    private static final Logger logger = Logger.getLogger(UploadPhotoCommand.class);

    /**
     * Name of directory to save photo
     */
    private static final String SAVE_DIRECTORY = "uploadFiles";

    /**
     * Empty string constant
     */
    private static final String EMPTY_STRING = "";

    /**
     * Back slash symbol constant
     */
    private static final String BACK_SLASH_SYMBOL = "\\";

    /**
     * User service instance
     */
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * Implementation of 'Upload user's photo' action
     *
     * @param req  the <code>HttpServletRequest</code> object contains the client's request
     * @param resp the <code>HttpServletResponse</code> object contains response to client
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Try upload user photo");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);
        Locale locale = (Locale) session.getAttribute(ParameterName.LOCALE);
        ResourceManager resourceManager = new ResourceManager(locale);

        try {
            Part file = req.getPart(ParameterName.PHOTO_FILE);
            String fileName = file.getSubmittedFileName();

            if (fileName == null || fileName.isEmpty()) {
                logger.warn("Uploading user photo canceled because file name is incorrect: " + fileName);
                String errorMessage = resourceManager.getValue(LocaleMessage.ERROR_INCORRECT_FILE_NAME);
                sendErrorUploading(req, resp, errorMessage);
                return;
            }

            String fullSavePath = pathToSavePhoto(req);
            createDirIfNoExist(fullSavePath);
            file.write(fullSavePath + File.separator + fileName);

            user.setPhotoPath(fileName);
            userService.update(user);

            resp.sendRedirect(PageName.PROFILE_URL
                    + URLConstant.PARAMETERS_SEPARATOR
                    + ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException | IOException e) {
            logger.warn("Uploading user photo canceled");
            String errorMessage = resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG);
            sendErrorUploading(req, resp, errorMessage);
        }
    }

    /**
     * Make path to directory of saving photos
     *
     * @param req the <code>HttpServletRequest</code> object contains the client's request
     * @return path to directory of saving photos
     */
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

    /**
     * Create directory if directory is not exist
     *
     * @param path path of directory
     */
    private void createDirIfNoExist(String path) {
        File fileSaveDir = new File(path);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    /**
     * Send response to client with error and message
     *
     * @param req          the <code>HttpServletRequest</code> object contains the client's request
     * @param resp         the <code>HttpServletResponse</code> object contains response to client
     * @param errorMessage message about error
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with operation
     */
    private void sendErrorUploading(HttpServletRequest req, HttpServletResponse resp, String errorMessage) throws ServletException, IOException {
        req.setAttribute(ParameterName.ERROR, errorMessage);
        req.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(req, resp);
    }
}
