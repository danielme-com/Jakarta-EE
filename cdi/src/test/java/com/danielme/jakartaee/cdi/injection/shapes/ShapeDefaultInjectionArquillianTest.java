package com.danielme.jakartaee.cdi.injection.shapes;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class ShapeDefaultInjectionArquillianTest {

    @Inject
    Shape shape;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.shapes();
    }

    @Test
    void testShapeIsCirle() {
        assertThat(shape).isInstanceOf(Circle.class);
    }

}