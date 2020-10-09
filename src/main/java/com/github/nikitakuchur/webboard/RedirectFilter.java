package com.github.nikitakuchur.webboard;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RedirectFilter implements Filter {

    private static final String STATIC_PATH = "/static/";
    private static final String API_PATH = "/api/";
    private static final String INDEX_PATH = STATIC_PATH + "index.html";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri.startsWith(STATIC_PATH) || uri.startsWith(API_PATH)) {
            chain.doFilter(request, response);
        } else {
            ServletContext servletContext = request.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(INDEX_PATH);
            requestDispatcher.forward(request, response);
        }
    }
}
