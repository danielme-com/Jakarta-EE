package com.danielme.jakartaee.cdi.scope;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopedDependency {

    private final Long timestamp;

    public RequestScopedDependency() {
        this.timestamp = System.currentTimeMillis();
    }

    Long getTimestamp() {
        return timestamp;
    }

}
