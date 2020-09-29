package by.epamtc.courses.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //stub
        resp.sendRedirect("/");
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute(USER_ATTRIBUTE, null);
        resp.sendRedirect("/");
    }
}
