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

@WebServlet(ConversationServlet.URL)
public class ConversationServlet extends HttpServlet {

    public static final String URL = "conversationServlet";

    private final ConversationScopedDependency conversationScoped;

    @Inject
    public ConversationServlet(ConversationScopedDependency conversationScoped) {
        this.conversationScoped = conversationScoped;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        response.setContentType(MediaType.TEXT_PLAIN);
        if (request.getParameter("init") != null) {
            printWriter.print(conversationScoped.init());
        } else if (request.getParameter("end") != null) {
            printWriter.print(conversationScoped.end());
        } else {
            printWriter.print(conversationScoped.doStep());
        }
    }

}
