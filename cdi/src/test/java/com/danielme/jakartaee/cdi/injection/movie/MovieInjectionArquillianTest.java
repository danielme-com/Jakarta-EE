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
class MovieInjectionArquillianTest {

    @Inject
    @Named("MovieImdbProvider")
    private MovieProvider movieImdbProvider;

    @Inject
    @Named("MovieTMDbProvider")
    private MovieProvider movieTMDbProvider;


    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.movies();
    }

    @Test
    void testProviderIsImdb() {
        assertThat(movieImdbProvider).isInstanceOf(MovieImdbProvider.class);
    }

    @Test
    void testProviderIsTMDb() {
        assertThat(movieTMDbProvider).isInstanceOf(MovieTMDbProvider.class);
    }

}