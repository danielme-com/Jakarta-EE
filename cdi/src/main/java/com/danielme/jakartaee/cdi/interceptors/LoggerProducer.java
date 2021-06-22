package com.danielme.jakartaee.cdi.interceptors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

public class LoggerProducer {

    @Produces
    @ApplicationScoped
    @Named("logger")
    List<String> logger() {
        return new ArrayList<>();
    }

    @Produces
    @ApplicationScoped
    @Named("constructors")
    List<String> constructors() {
        return new ArrayList<>();
    }
}
