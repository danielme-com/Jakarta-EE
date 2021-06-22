package com.danielme.jakartaee.cdi.scope;

import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

@SessionScoped
public class SessionScopedDependency implements Serializable {

    private final Long timestamp;

    public SessionScopedDependency() {
        this.timestamp = System.currentTimeMillis();
    }

    Long getTimestamp() {
        return timestamp;
    }

}
