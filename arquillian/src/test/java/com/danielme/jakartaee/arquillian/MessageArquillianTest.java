package com.danielme.jakartaee.arquillian;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class MessageArquillianTest {

    private static final String HELLO_MESSAGE = "Jakarta EE rocks!!";

    @Inject
    private Message service;

    @ArquillianResource
    URL contextPath;

    @Deployment
    public static WebArchive createDeployment() {
        File[] assertjFiles = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class, "arquillian-test.war")
                .addClass(Message.class)
                .addClass(HelloServlet.class)
                .addAsLibraries(assertjFiles);
    }

    @Test
    @DisplayName("probando inyección de Message")
    void testMessageBean() {
        assertThat(service.get()).isEqualTo(HELLO_MESSAGE);
    }

    @Test
    @RunAsClient
    @DisplayName("cliente HTTP que llama al servlet")
    void testHelloServlet() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(contextPath + HelloServlet.URL))
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.body()).isEqualTo(HELLO_MESSAGE);
    }

}