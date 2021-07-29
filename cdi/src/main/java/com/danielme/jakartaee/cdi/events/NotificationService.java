package com.danielme.jakartaee.cdi.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class NotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);

    @Inject
    @Named("notifications")
    private List<String> notifications;

    void documentCreationObserver(@Observes DocumentSavedEvent documentSavedEvent) {
        notifications.add(Observes.class.getSimpleName() + documentSavedEvent.getDocument().getName());
        log.info("notification sended");
    }

    void documentCreationObserverAsync(@ObservesAsync DocumentSavedEvent documentSavedEvent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            log.error(ex.getMessage(), ex);
        }
        notifications.add(ObservesAsync.class.getSimpleName() + documentSavedEvent.getDocument().getName());
        log.info("notification sended async");
    }

}
