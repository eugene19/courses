package by.epamtc.courses.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //stub
        resp.sendRedirect("/");
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().setAttribute("user", null);
        resp.sendRedirect("/");
    }
}
