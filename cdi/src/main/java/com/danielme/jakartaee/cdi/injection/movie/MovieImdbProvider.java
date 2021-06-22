package com.danielme.jakartaee.cdi.injection.movie;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("MovieImdbProvider")
public class MovieImdbProvider implements MovieProvider {
}
