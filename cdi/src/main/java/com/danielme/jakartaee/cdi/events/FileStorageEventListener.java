package com.danielme.jakartaee.cdi.events;

import com.danielme.jakartaee.cdi.injection.file.FileContent;
import com.danielme.jakartaee.cdi.injection.file.FileStorageLocalQualifier;
import com.danielme.jakartaee.cdi.injection.file.FileStorageRemoteQualifier;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

public class FileStorageEventListener {

    @Inject
    @Named("notifications")
    private List<String> notifications;

    void observerLocal(@Observes @FileStorageLocalQualifier FileContent fileContent) {
        notifications.add(FileStorageLocalQualifier.class.getSimpleName() + fileContent.getName());
    }

    void observerRemote(@Observes @FileStorageRemoteQualifier FileContent fileContent) {
        notifications.add(FileStorageRemoteQualifier.class.getSimpleName() + fileContent.getName());
    }

}
