package by.epamtc.courses.controller.filter;

import by.epamtc.courses.controller.command.CommandName;
import by.epamtc.courses.entity.ParameterName;
import by.epamtc.courses.service.PageName;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String command = req.getParameter(ParameterName.COMMAND);
        Object user = req.getSession(true).getAttribute(ParameterName.USER);

        // TODO: 10/8/20 Refactoring this code to map<page, role[]>

        if (command != null
                && (command.equalsIgnoreCase(CommandName.GET_PROFILE_PAGE.toString())
                || command.equalsIgnoreCase(CommandName.GET_EDIT_PROFILE_PAGE.toString())
                || command.equalsIgnoreCase(CommandName.GET_ADD_COURSE_PAGE.toString()))
                && user == null) {
            LOGGER.warn("Try opening private page not authored user");
            req.getRequestDispatcher(PageName.LOGIN_PAGE).forward(req, resp);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
