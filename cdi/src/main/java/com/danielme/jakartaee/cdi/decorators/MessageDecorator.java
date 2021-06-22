package com.danielme.jakartaee.cdi.decorators;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Decorator
public abstract class MessageDecorator implements MessageService {

    @Inject
    @Named("logger")
    private List<String> logger;

    @Inject
    @Delegate
    @Any
    private MessageService messageService;

    @Override
    public String message1() {
        String msg = messageService.message1();
        logger.add(msg);
        return msg;
    }

}
