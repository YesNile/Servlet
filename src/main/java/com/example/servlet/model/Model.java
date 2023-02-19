package com.example.servlet.model;

import java.io.File;
import java.util.Date;

public class Model {
    private final File file;
    private final long length;
    private final Date lastModified;

    public Date getLastModified() {
        return lastModified;
    }

    public File getFile() {
        return file;
    }

    public long getLength() {
        return length;
    }

    public Model(File file, long length, Date lastModified) {
        this.file = file;
        this.length = length;
        this.lastModified = lastModified;
    }
}
