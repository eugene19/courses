package by.epamtc.courses.controller.command.page;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.service.PageName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<String> news = new ArrayList<>();
        news.add("One news");
        news.add("Two news");
        news.add("Three news");

        req.setAttribute("news", news);
        req.getRequestDispatcher(PageName.NEWS_PAGE).forward(req, resp);
    }
}
