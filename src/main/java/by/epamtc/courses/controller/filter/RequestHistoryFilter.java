package by.epamtc.courses.controller.filter;

import by.epamtc.courses.entity.ParameterName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            Map<String, String[]> parameterMap = req.getParameterMap();

            session.setAttribute(ParameterName.PREVIOUS_COMMAND, copyRequestParameters(parameterMap));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private Map<String, String[]> copyRequestParameters(Map<String, String[]> src) {
        HashMap<String, String[]> dest = new HashMap<>();

        for (Map.Entry<String, String[]> srcParameterPair : src.entrySet()) {
            if (srcParameterPair.getKey().equalsIgnoreCase(ParameterName.PASSWORD)) {
                continue;
            }
            dest.put(srcParameterPair.getKey(), srcParameterPair.getValue());
        }

        return dest;
    }
}
