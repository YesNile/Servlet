package com.example.servlet.model;

import java.io.File;
import java.util.Date;

public class Model {
    private final File file;
    private final long length;
    private final String lastModified;

    public String getLastModified() {
        return lastModified;
    }

    public File getFile() {
        return file;
    }

    public long getLength() {
        return length;
    }

    public Model(File file, long length, String lastModified) {
        this.file = file;
        this.length = length;
        this.lastModified = lastModified;
    }
}
