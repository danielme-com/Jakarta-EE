package com.danielme.jakartaee.cdi.injection.movie;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("MovieTMDbProvider")
public class MovieTMDbProvider implements MovieProvider {
}
