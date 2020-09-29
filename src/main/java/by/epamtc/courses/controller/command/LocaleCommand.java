package by.epamtc.courses.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {

    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //stub
        resp.sendRedirect("/");
    }

    @Override
    public void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locale = req.getParameter(LOCALE_ATTRIBUTE);
        HttpSession session = req.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);

        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }
}
