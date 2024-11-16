package com.example.TodoProjectManager.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        System.out.println("Request Path: " + path);

        // Allow CORS preflight requests and auth endpoints without token check
        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS") || path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        String token = httpRequest.getHeader("Authorization");

        // Validate session and token
        if (session == null || token == null || !token.equals(session.getAttribute("token"))) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing token.\"}");
            return;
        }

        // Proceed with the filter chain if session and token are valid
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional: initialization code if needed
    }

    @Override
    public void destroy() {
        // Optional: cleanup code if needed
    }
}
