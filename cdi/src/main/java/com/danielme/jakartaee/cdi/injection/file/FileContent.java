package com.danielme.jakartaee.cdi.injection.file;

public class FileContent {

    private final String name;
    private final byte[] content;

    public FileContent(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

}
