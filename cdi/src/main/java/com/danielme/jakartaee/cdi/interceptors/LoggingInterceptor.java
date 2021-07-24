package com.danielme.jakartaee.cdi.interceptors;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.AroundConstruct;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.List;

@Interceptor
@Logging
public class LoggingInterceptor {

    @Inject
    @Named("logger")
    private List<String> logger;

    @Inject
    @Named("constructors")
    private List<String> constructors;

    @AroundConstruct
    private Object construct(InvocationContext context) throws Exception {
        constructors.add(context.getConstructor().getDeclaringClass().getSimpleName());
        return context.proceed();
    }

    @AroundInvoke
    private Object log(InvocationContext context) throws Exception {
        logger.add(context.getMethod().getName());
        return context.proceed();
    }

}
