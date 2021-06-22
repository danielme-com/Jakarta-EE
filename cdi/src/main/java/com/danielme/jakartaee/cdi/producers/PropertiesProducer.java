package com.danielme.jakartaee.cdi.producers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
public class PropertiesProducer {

    private static final String CONFIG_PROPERTIES = "config.properties";
    private Properties properties;

    @PostConstruct
    public void loadProperties() throws IOException {
        properties = new Properties();
        properties.load(PropertiesProducer.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES));
    }

    @Produces
    @ConfigProperty("")
    String getProperty(InjectionPoint injectionPoint) {
        String key = injectionPoint.getAnnotated().getAnnotation(ConfigProperty.class).value();
        return properties.getProperty(key);
    }

}
