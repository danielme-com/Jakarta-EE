package com.danielme.jakartaee.cdi.producers;

import com.danielme.jakartaee.cdi.injection.file.FileStorage;
import com.danielme.jakartaee.cdi.injection.file.FileStorageLocalQualifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.List;

public class ListsProducer {

    @Produces
    @Named("colors")
    @ApplicationScoped
    List<String> produceColors() {
        return List.of("RED", "BLUE");
    }

    @Produces
    @Named("names")
    @ApplicationScoped
    List<String> produceNames() {
        return List.of("John", "Elaine");
    }

    @Produces
    @Named("filesAvailable")
    @ApplicationScoped
    private List<String> fetchAvailableFiles(@FileStorageLocalQualifier FileStorage fileStorage) {
        return fileStorage.availableFiles();
    }

}
