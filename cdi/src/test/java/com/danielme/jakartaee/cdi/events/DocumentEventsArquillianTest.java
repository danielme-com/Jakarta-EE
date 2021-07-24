package com.danielme.jakartaee.cdi.events;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.assertj.core.api.SoftAssertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;

@ExtendWith(ArquillianExtension.class)
public class DocumentEventsArquillianTest {

    @Inject
    @Named("notifications")
    private List<String> notifications;

    @Inject
    private DocumentStorage documentStorage;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.filesAndEvents();
    }

    @Test
    void testDocumentObserver() {
        Document document = new Document("test", new byte[]{});

        documentStorage.add(document);

        await()
                .atMost(Duration.ofSeconds(3))
                .until(() -> {
                    SoftAssertions softly = new SoftAssertions();
                    softly.assertThat(notifications)
                            .containsExactly(Observes.class.getSimpleName() + document.getName(),
                                             ObservesAsync.class.getSimpleName() + document.getName());
                    return softly.wasSuccess();
                });
    }

}
