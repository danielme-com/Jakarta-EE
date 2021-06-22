package com.danielme.jakartaee.cdi.injection.file;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ExtendWith(ArquillianExtension.class)
class FileStorageInjectionArquillianTest {

    @Inject
    @FileStorageRemoteQualifier
    private FileStorage fileStorageRemote;

    @Inject
    @FileStorageLocalQualifier
    private FileStorage fileStorageLocal;

    @Inject
    private FileStorage fileStorageDefault;

    @Any
    @Inject
    Instance<FileStorage> storageImplementations;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.filesAndEvents();
    }

    @Test
    void testFileStorageIsLocal() {
        assertThat(fileStorageLocal).isInstanceOf(FileStorageLocal.class);
    }

    @Test
    void testFileStorageIsRemote() {
        assertThat(fileStorageRemote).isInstanceOf(FileStorageRemote.class);
    }

    @Test
    void testFileStorageDefault() {
        assertThat(fileStorageDefault).isInstanceOf(FileStorageTemp.class);
    }

    @Test
    void testAnyInjectAll() {
        assertThatCode(() -> storageImplementations.select(FileStorageLocal.class).get())
                .doesNotThrowAnyException();
        assertThatCode(() -> storageImplementations.select(FileStorageRemote.class).get())
                .doesNotThrowAnyException();
        assertThatCode(() -> storageImplementations.select(FileStorageTemp.class).get())
                .doesNotThrowAnyException();
    }

}