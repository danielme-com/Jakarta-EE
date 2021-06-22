package com.danielme.jakartaee.cdi.injection.movie;

import com.danielme.jakartaee.cdi.Deployments;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class MovieAlternativeInjectionArquillianTest {

    @Inject
    @Named("MovieTMDbProvider")
    private MovieProvider movieTMDbProvider;


    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.moviesAlternatives();
    }

    @Test
    void testProviderTMDbIsAlternative() {
        assertThat(movieTMDbProvider).isInstanceOf(MovieTMDbV2Provider.class);
    }

}