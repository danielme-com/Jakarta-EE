package com.danielme.jakartaee.cdi.scope;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(ScopesServlet.URL)
public class ScopesServlet extends HttpServlet {

    public static final String URL = "scopesServlet";

    private static final String SCOPE_PARAM = "scope";

    public enum Scope {
        APPLICATION, REQUEST, SESSION
    }

    private final ApplicationScopedDependency applicationScoped;
    private final RequestScopedDependency requestScoped;
    private final SessionScopedDependency sessionScoped;

    @Inject
    public ScopesServlet(ApplicationScopedDependency applicationScoped,
                         RequestScopedDependency requestScoped,
                         SessionScopedDependency sessionScoped) {
        this.applicationScoped = applicationScoped;
        this.requestScoped = requestScoped;
        this.sessionScoped = sessionScoped;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        response.setContentType(MediaType.TEXT_PLAIN);
        printWriter.print(getTimestamp(request));
    }

    private Long getTimestamp(HttpServletRequest request) {
        String scopeParam = request.getParameter(SCOPE_PARAM);
        if (Scope.APPLICATION.name().equals(scopeParam)) {
            return applicationScoped.getTimestamp();
        }
        if (Scope.REQUEST.name().equals(scopeParam)) {
            return requestScoped.getTimestamp();
        }
        if (Scope.SESSION.name().equals(scopeParam)) {
            return sessionScoped.getTimestamp();
        }
        throw new IllegalArgumentException("invalid 'scope' param");
    }

}
