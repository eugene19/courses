package by.epamtc.courses.controller.filter;

import by.epamtc.courses.constant.ParameterName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The filter class is designed to save previous user's request
 *
 * @author DEA
 */
public class RequestHistoryFilter implements Filter {

    /**
     * Save all request with params except request on change language because
     * sometimes need repeat previous user's request
     *
     * @param servletRequest  the <code>ServletRequest</code> object contains the client's request
     * @param servletResponse the <code>ServletResponse</code> object contains the filter's response
     * @param filterChain     the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String command = req.getParameter(ParameterName.COMMAND);

        if (command != null && !command.equalsIgnoreCase(ParameterName.LOCALE)) {
            HttpSession session = req.getSession(true);
            Map<String, String[]> parameters = req.getParameterMap();

            session.setAttribute(ParameterName.PREVIOUS_COMMAND, copyParameters(parameters));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Copy all request parameters except password from one Map to another
     *
     * @param src source request's parameters
     * @return copy of Map containing request's parameters except password
     */
    private Map<String, String[]> copyParameters(Map<String, String[]> src) {
        HashMap<String, String[]> dest = new HashMap<>(src);

        // don't save password
        dest.remove(ParameterName.PASSWORD);

        return dest;
    }
}
