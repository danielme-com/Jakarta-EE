package com.danielme.jakartaee.cdi.scope;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopedDependency {

    private final Long timestamp;

    public ApplicationScopedDependency() {
        this.timestamp = System.currentTimeMillis();
    }

    Long getTimestamp() {
        return timestamp;
    }

}
