package com.danielme.jakartaee.cdi.events;

public class DocumentSavedEvent {

    private final Document document;

    public DocumentSavedEvent(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

}
