package com.danielme.jakartaee.cdi.producers;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class ProducersAlternativeArquillianTest {

    @Inject
    @Named("names")
    private List<String> names;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.producersAlternatives();
    }

    @Test
    void testNamesList() {
        assertThat(names).containsExactly("Michael");
    }

}