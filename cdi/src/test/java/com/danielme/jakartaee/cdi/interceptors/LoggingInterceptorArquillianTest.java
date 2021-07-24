package com.danielme.jakartaee.cdi.interceptors;

import com.danielme.jakartaee.cdi.Deployments;
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
public class LoggingInterceptorArquillianTest {

    @Inject
    private TargetClass targetClass;

    @Inject
    @Named("logger")
    private List<String> logger;

    @Inject
    @Named("constructors")
    private List<String> constructors;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.interceptors();
    }

    @AfterEach
    void cleanUp() {
        if (logger != null)
            logger.clear();
    }

    @Test
    void testInterceptorMethod1() {
        targetClass.methodToIntercept1();

        assertThat(logger).containsExactly("methodToIntercept1");
    }

    @Test
    void testInterceptorMethod2() {
        targetClass.methodToIntercept2();

        assertThat(logger).containsExactly("methodToIntercept2");
    }

    @Test
    void testConstructor() {
        assertThat(constructors).containsExactly(TargetClass.class.getSimpleName());
    }

}
