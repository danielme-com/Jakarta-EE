package com.danielme.jakartaee.cdi.injection.movie;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Named;

@ApplicationScoped
@Named("MovieTMDbProvider")
@Alternative
public class MovieTMDbV2Provider implements MovieProvider {
}
