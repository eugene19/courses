package by.epamtc.courses.controller.filter;

import by.epamtc.courses.entity.ParameterName;

import javax.servlet.*;
import java.io.IOException;

/**
 * The filter class is designed to set the encoding of the request and response
 *
 * @author DEA
 */
public class CharacterFilter implements Filter {

    /**
     * Field containing name of charset
     */
    private String charset;

    /**
     * Init charset field by charset value from init param of filter config
     *
     * @param filterConfig the <code>FilterConfig</code> object that contains
     *                     configuration information for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        charset = filterConfig.getInitParameter(ParameterName.CHARSET);
    }

    /**
     * Set charset to each request and response
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
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
