package com.danielme.jakartaee.cdi.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.List;

@Alternative
public class ListAlternativesProducer {

    @Produces
    @Named("names")
    @ApplicationScoped
    List<String> produceNamesAlternative() {
        return List.of("Michael");
    }
}
