package com.romantupikov.simpleapp.filters;

import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter extends HttpFilter {

    // list URL that want to exclude from authentication filter
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login.xhtml", "/register.xhtml")
    ));

    @Override
    public void doFilter(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         HttpSession httpSession, FilterChain filterChain) throws ServletException, IOException {

        String loginUrl = httpServletRequest.getContextPath() + "/login.xhtml";
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length())
                .replaceAll("[/]+$", "");

        // check if user already logged or not, session for authenticate user was on this getRemoteUser()
        boolean loggedIn = (httpServletRequest.getRemoteUser() != null);
        // check if the URL was appointed to login URL or not
        boolean loginRequest = httpServletRequest.getRequestURI().equals(loginUrl);
        boolean resourceRequest = Servlets.isFacesResourceRequest(httpServletRequest);
        // check if the URL was allowed or not
        boolean allowedUrl = ALLOWED_PATHS.contains(path);

        if (loggedIn || loginRequest || resourceRequest || allowedUrl) {
            if (!resourceRequest) { // Prevent browser from caching restricted resources
                Servlets.setNoCacheHeaders(httpServletResponse);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse); // So, just continue request.
        } else {
            Servlets.facesRedirect(httpServletRequest, httpServletResponse, loginUrl);
        }
    }
}
