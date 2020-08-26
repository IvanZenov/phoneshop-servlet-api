package com.es.phoneshop.web.filter;

import com.es.phoneshop.security.DefaultDosService;
import com.es.phoneshop.security.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DefaultDosService defaultDosService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultDosService = DosServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (defaultDosService.isAllowed(servletRequest.getRemoteAddr()))
        {
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            ((HttpServletResponse)servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
