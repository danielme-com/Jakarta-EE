package com.danielme.jakartaee.cdi.decorators;

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
public class DecoratorArquillianTest {

    @Inject
    private MessageService messageService;

    @Inject
    @Named("logger")
    private List<String> logger;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.decorators();
    }

    @Test
    void testDecorateMethod1() {
        String msg = messageService.message1();

        assertThat(logger).containsExactly(msg);
    }

}
