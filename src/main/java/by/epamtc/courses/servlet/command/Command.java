package by.epamtc.courses.servlet.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

    void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}
