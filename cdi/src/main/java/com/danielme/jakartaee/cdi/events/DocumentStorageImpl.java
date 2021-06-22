package com.danielme.jakartaee.cdi.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class DocumentStorageImpl implements DocumentStorage {

    @Inject
    Event<DocumentSavedEvent> event;

    @Override
    public void add(Document document) {
        event.fire(new DocumentSavedEvent(document));
        event.fireAsync(new DocumentSavedEvent(document));
        System.out.println("document saved");
    }

}
