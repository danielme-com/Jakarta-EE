package com.danielme.jakartaee.cdi.scope;

import com.danielme.jakartaee.cdi.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class ConversationServletArquillianTest {

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.scopes();
    }

    @Test
    @DisplayName("Simula una conversación")
    void testSessionScopeEqualServlet() throws IOException, InterruptedException {
        CookieHandler.setDefault(new CookieManager());
        HttpClient client = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                .build();

        HttpRequest requestInit = HttpRequest.newBuilder()
                .uri(URI.create(contextPath + ConversationServlet.URL + "?init="))
                .build();
        HttpResponse<String> response1 =
                client.send(requestInit, HttpResponse.BodyHandlers.ofString());
        String cid = response1.body();

        HttpRequest requestStep = HttpRequest.newBuilder()
                .uri(URI.create(contextPath + ConversationServlet.URL + "?cid=" + cid))
                .build();
        HttpResponse<String> responseStep =
                client.send(requestStep, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestStop = HttpRequest.newBuilder()
                .uri(URI.create(contextPath + ConversationServlet.URL + "?cid=" + cid + "&end"))
                .build();
        HttpResponse<String> responseStop =
                client.send(requestStop, HttpResponse.BodyHandlers.ofString());

        HttpRequest requestClosed = HttpRequest.newBuilder()
                .uri(URI.create(contextPath + ConversationServlet.URL + "?cid=" + cid))
                .build();
        HttpResponse<String> responseClosed =
                client.send(requestClosed, HttpResponse.BodyHandlers.ofString());

        assertThat(responseStep.body()).isEqualTo("1");
        assertThat(responseStop.body()).isEqualTo("1");
        assertThat(responseClosed.statusCode()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

}