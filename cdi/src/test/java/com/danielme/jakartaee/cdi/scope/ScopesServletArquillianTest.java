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
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class ScopesServletArquillianTest {

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.scopes();
    }


    HttpRequest setup(ScopesServlet.Scope scope) {
        return HttpRequest.newBuilder()
                .uri(URI.create(contextPath + ScopesServlet.URL + "?scope=" + scope))
                .build();
    }

    @Test
    @DisplayName("ApplicationScoped es siempre el mismo")
    void testApplicationScopeServlet() throws IOException, InterruptedException {
        HttpRequest request = setup(ScopesServlet.Scope.APPLICATION);

        HttpResponse<String> response1 =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response1.body()).isEqualTo(response2.body());
    }

    @Test
    @DisplayName("RequestScoped es siempre distinto")
    void testRequestScopeServlet() throws IOException, InterruptedException {
        HttpRequest request = setup(ScopesServlet.Scope.REQUEST);

        HttpResponse<String> response1 =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response1.body()).isNotEqualTo(response2.body());
    }

    @Test
    @DisplayName("SessionScoped es el mismo dentro de una sesión")
    void testSessionScopeEqualServlet() throws IOException, InterruptedException {
        HttpRequest request = setup(ScopesServlet.Scope.SESSION);
        CookieHandler.setDefault(new CookieManager());
        HttpClient client = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                .build();

        HttpResponse<String> response1 =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response1.body()).isEqualTo(response2.body());
    }

    @Test
    @DisplayName("SessionScoped es distinto para sesiones distintas")
    void testSessionScopeNotEqualServlet() throws IOException, InterruptedException {
        HttpRequest request = setup(ScopesServlet.Scope.SESSION);

        HttpResponse<String> response1 =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response1.body()).isNotEqualTo(response2.body());
    }

}