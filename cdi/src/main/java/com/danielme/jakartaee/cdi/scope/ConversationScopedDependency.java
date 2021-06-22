package com.danielme.jakartaee.cdi.scope;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;

import java.io.Serializable;

@ConversationScoped
public class ConversationScopedDependency implements Serializable {

    private Conversation conversation;
    private int steps;

    public ConversationScopedDependency() {
    }

    @Inject
    public ConversationScopedDependency(Conversation conversation) {
        this.conversation = conversation;
    }

    String init() {
        conversation.begin();
        return conversation.getId();
    }

    int doStep() {
        return ++steps;
    }

    int end() {
        conversation.end();
        return steps;
    }

}