package by.epamtc.courses.service.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String locale = req.getParameter("locale");
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);

        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
//        resp.sendRedirect("/");
    }
}
