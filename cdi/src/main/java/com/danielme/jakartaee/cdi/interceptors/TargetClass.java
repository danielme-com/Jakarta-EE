package com.danielme.jakartaee.cdi.interceptors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.interceptor.Interceptors;

//@Interceptors(LoggingInterceptor.class)
@Logging
@ApplicationScoped
public class TargetClass {

    public TargetClass() {
    }

    public void methodToIntercept1() {
        methodToIntercept2();
    }

    public void methodToIntercept2() {
    }

}
