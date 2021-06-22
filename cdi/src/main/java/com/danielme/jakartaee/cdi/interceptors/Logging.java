package com.danielme.jakartaee.cdi.interceptors;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@InterceptorBinding
public @interface Logging {
}
