package com.danielme.jakartaee.cdi.events;

import com.danielme.jakartaee.cdi.Deployments;
import com.danielme.jakartaee.cdi.injection.file.FileContent;
import com.danielme.jakartaee.cdi.injection.file.FileStorage;
import com.danielme.jakartaee.cdi.injection.file.FileStorageLocalQualifier;
import com.danielme.jakartaee.cdi.injection.file.FileStorageRemoteQualifier;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
public class FileStorageEventsArquillianTest {

    @Inject
    @Named("notifications")
    private List<String> notifications;

    @Inject
    @FileStorageLocalQualifier
    private FileStorage fileStorageLocal;

    @Inject
    @FileStorageRemoteQualifier
    private FileStorage fileStorageRemote;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.filesAndEvents();
    }

    @AfterEach
    void cleanUp() {
        if (notifications != null)
            notifications.clear();
    }

    @Test
    void testLocalObserver() {
        FileContent fileContent = new FileContent("test-local", new byte[]{});

        fileStorageLocal.add(fileContent);

        assertThat(notifications).containsExactly(FileStorageLocalQualifier.class.getSimpleName() + fileContent.getName());
    }

    @Test
    void testRemoteObserver() {
        FileContent fileContent = new FileContent("test-remote", new byte[]{});

        fileStorageRemote.add(fileContent);

        assertThat(notifications).containsExactly(FileStorageRemoteQualifier.class.getSimpleName() + fileContent.getName());
    }

}
