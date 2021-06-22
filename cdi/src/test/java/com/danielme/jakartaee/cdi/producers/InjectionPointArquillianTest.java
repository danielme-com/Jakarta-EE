package com.danielme.jakartaee.cdi.producers;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class InjectionPointArquillianTest {

    @Inject
    @ConfigProperty("key1")
    private String key1;

    @Inject
    @ConfigProperty("key2")
    private String key2;

    @Inject
    @ConfigProperty("undefined")
    private String undefined;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.injectionPoint();
    }

    @Test
    void testGetByAnnotationValue() {
        assertThat(key1).isEqualTo("value1");
    }

    @Test
    void testGetByMemberName() {
        assertThat(key2).isEqualTo("value2");
    }

    @Test
    void testUndefinedProperty() {
        assertThat(undefined).isNull();
    }

}