package com.danielme.jakartaee.cdi.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
public class NotificationService {

    @Inject
    @Named("notifications")
    private List<String> notifications;

    void documentCreationObserver(@Observes DocumentSavedEvent documentSavedEvent) {
        notifications.add(Observes.class.getSimpleName() + documentSavedEvent.getDocument().getName());
        System.out.println("notification sended");
    }

    void documentCreationObserverAsync(@ObservesAsync DocumentSavedEvent documentSavedEvent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifications.add(ObservesAsync.class.getSimpleName() + documentSavedEvent.getDocument().getName());
        System.out.println("notification sended async");
    }

}
