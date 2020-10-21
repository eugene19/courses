package by.epamtc.courses.controller.command.course;

import by.epamtc.courses.URLConstant;
import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.entity.User;
import by.epamtc.courses.service.CourseService;
import by.epamtc.courses.service.PageName;
import by.epamtc.courses.service.ServiceException;
import by.epamtc.courses.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class UploadCourseMaterialsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UploadCourseMaterialsCommand.class);

    private static final String SAVE_DIRECTORY = "uploadFiles";
    private static final String EMPTY_STRING = "";
    private static final String BACK_SLASH_SYMBOL = "\\";

    private CourseService courseService = ServiceProvider.getInstance().getCourseService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.debug("Try upload course's materials");

        String courseIdStr = req.getParameter(ParameterName.COURSE_ID);
        Part courseMaterials = req.getPart(ParameterName.MATERIALS);

        try {
            int courseId = Integer.parseInt(courseIdStr);
            String fileName = courseMaterials.getSubmittedFileName();

            if (fileName == null || fileName.isEmpty()) {
                LOGGER.warn("Uploading user photo canceled because file name is incorrect: " + fileName);
                resp.sendRedirect(PageName.COURSE_DETAILS_URL + courseIdStr +
                        URLConstant.PARAMETERS_SEPARATOR +
                        ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + false);
                return;
            }

            String fullSavePath = pathToSaveFile(req);
            createDirIfNoExist(fullSavePath);

            courseMaterials.write(fullSavePath + File.separator + fileName);

            courseService.updateMaterialPath(courseId, fileName);

            resp.sendRedirect(PageName.COURSE_DETAILS_URL + courseIdStr +
                    URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + true);
        } catch (ServiceException | IOException e) {
            LOGGER.warn("Uploading course's materials error");
            resp.sendRedirect(PageName.COURSE_DETAILS_URL + courseIdStr +
                    URLConstant.PARAMETERS_SEPARATOR +
                    ParameterName.IS_UPDATING_OK + URLConstant.KEY_VALUE_SEPARATOR + false);
        }
    }

    private String pathToSaveFile(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute(ParameterName.USER);

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
}
