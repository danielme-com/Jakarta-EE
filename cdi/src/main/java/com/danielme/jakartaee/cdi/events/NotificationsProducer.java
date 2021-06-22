package com.danielme.jakartaee.cdi.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

public class NotificationsProducer {

    @Produces
    @Named("notifications")
    @ApplicationScoped
    List<String> notifications() {
        return new ArrayList<>();
    }

}
