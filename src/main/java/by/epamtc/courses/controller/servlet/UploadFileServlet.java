package by.epamtc.courses.controller.servlet;

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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {
    public static final String SAVE_DIRECTORY = "uploadFiles";
    private static final long serialVersionUID = 3763827941580642076L;
    private static final Logger LOGGER = Logger.getLogger(UploadFileServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        try {
            String appPath = request.getServletContext().getRealPath("");
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

            Part file = request.getPart("file");
            String fileName = file.getSubmittedFileName();
            if (fileName != null && fileName.length() > 0) {
                String filePath = fullSavePath + File.separator + fileName;
                file.write(filePath);
            }

            user.setPhotoPath(fileName);

            UserService userService = ServiceProvider.getInstance().getUserService();
            userService.update(user);

            response.sendRedirect("/main?"
                    + ParameterName.COMMAND + "=" + CommandName.GET_PROFILE_PAGE +
                    "&" + ParameterName.IS_UPDATING_OK + "=" + true);
        } catch (Exception e) {
            LOGGER.warn("Updating user canceled because user's data is invalid");
            ResourceManager resourceManager = new ResourceManager((Locale) session.getAttribute(ParameterName.LOCALE));
            request.setAttribute(ParameterName.ERROR, resourceManager.getValue(LocaleMessage.SOMETHING_GOES_WRONG));
            request.getRequestDispatcher(PageName.EDIT_PROFILE_PAGE).forward(request, response);
        }
    }
}
