package com.danielme.jakartaee.cdi.injection.file;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FileStorageTemp implements FileStorage {

    @Override
    public List<String> availableFiles() {
        return null;
    }

    @Override
    public void add(FileContent fileContent) {

    }
}
