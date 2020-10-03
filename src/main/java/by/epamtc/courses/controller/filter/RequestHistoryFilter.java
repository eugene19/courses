package by.epamtc.courses.controller.filter;

import by.epamtc.courses.entity.ParameterName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestHistoryFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String command = req.getParameter(ParameterName.COMMAND);

        if (command != null && !command.equals(ParameterName.LOCALE)) {
            HttpSession session = req.getSession(true);
            session.setAttribute(ParameterName.PREVIOUS_COMMAND, command);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
