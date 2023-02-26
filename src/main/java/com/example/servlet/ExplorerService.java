package com.example.servlet;

import com.example.servlet.model.Model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class ExplorerService {
    public File getUserFiles(String login, String path) {
        if (path == null) {
            path = "";
        }
        File file = getUserRootFolder(login);
        path = path.startsWith("/" + login + "/") ? path.split(login + "/")[1] : "";
        return new File(file.getAbsolutePath() + "\\" + path);
    }

    private File getUserRootFolder(String login) {
        File root = new File("D:\\JavaServlet");
        boolean isFolderExist = false;
        for (File file : root.listFiles()) {
            if (file.isDirectory() && file.getName().equals(login)) {
                isFolderExist = true;
            }
        }

        if (!isFolderExist) {
            File file = new File(root.getAbsolutePath() + "\\" + login);
            file.mkdir();
        }

        return new File(root.getAbsolutePath() + "\\" + login);
    }

    public Model[] getSubDirectory(File file) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy ss:mm:hh a");
        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory)
                .map(x -> new Model(x, x.length(), format.format(new Date(x.lastModified()))))
                .toArray(Model[]::new);
    }

    public Model[] getSubFile(File file) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy ss:mm:hh a");
        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isFile)
                .map(x -> new Model(x, x.length(), format.format(new Date(x.lastModified()))))
                .toArray(Model[]::new);
    }
}