package com.danielme.jakartaee.cdi.injection.file;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
@FileStorageLocalQualifier
public class FileStorageLocal implements FileStorage {

    @Inject
    @FileStorageLocalQualifier
    private Event<FileContent> event;

    @Override
    public void add(FileContent fileContent) {
        event.fire(fileContent);
    }

    @Override
    public List<String> availableFiles() {
        return List.of("JakartaEE.pdf");
    }

}
