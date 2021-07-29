package com.danielme.jakartaee.cdi.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DocumentStorageImpl implements DocumentStorage {

    private static final Logger log =
            LoggerFactory.getLogger(DocumentStorageImpl.class);

    @Inject
    Event<DocumentSavedEvent> event;

    @Override
    public void add(Document document) {
        event.fire(new DocumentSavedEvent(document));
        event.fireAsync(new DocumentSavedEvent(document));
        log.info("document saved");
    }

}
