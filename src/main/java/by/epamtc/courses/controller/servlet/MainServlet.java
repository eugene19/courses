package by.epamtc.courses.controller.servlet;

import by.epamtc.courses.controller.command.Command;
import by.epamtc.courses.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 8717762438987053788L;

    private CommandProvider provider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = provider.getCommand(req.getParameter("command"));
        command.executeGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = provider.getCommand(req.getParameter("command"));
        command.executePost(req, resp);
    }
}
