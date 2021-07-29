package com.danielme.jakartaee.cdi.injection.file;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
@FileStorageRemoteQualifier
public class FileStorageRemote implements FileStorage {

    @Inject
    @FileStorageRemoteQualifier
    private Event<FileContent> event;

    @Override
    public void add(FileContent fileContent) {
        event.fire(fileContent);
    }

    @Override
    public List<String> availableFiles() {
        return null;
    }

}
