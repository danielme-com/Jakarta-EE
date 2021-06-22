package com.danielme.jakartaee.cdi.decorators;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageServiceImpl implements MessageService {

    @Override
    public String message1() {
        return "message 1";
    }

    @Override
    public String message2() {
        return "message 2";
    }

}
