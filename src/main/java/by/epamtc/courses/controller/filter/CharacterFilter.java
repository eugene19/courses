package by.epamtc.courses.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterFilter implements Filter {

    private String charset;

    @Override
    public void init(FilterConfig filterConfig) {
        charset = filterConfig.getInitParameter("charset");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
