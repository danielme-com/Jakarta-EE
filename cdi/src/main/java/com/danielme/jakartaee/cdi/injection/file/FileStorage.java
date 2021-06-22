package com.danielme.jakartaee.cdi.injection.file;

import java.util.List;

public interface FileStorage {

    List<String> availableFiles();

    void add(FileContent document);
}
