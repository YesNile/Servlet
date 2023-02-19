package com.example.servlet;

import com.example.servlet.model.Model;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@WebServlet("/")
public class ExplorerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")));

        String pathVariable = request.getParameter("path");

        if (pathVariable == null || pathVariable.equals("")) {
            pathVariable = "D:/";
        }
        pathVariable = pathVariable.replaceAll("%20", " ");

        File file = readFile(pathVariable);
        request.setAttribute("path", file.getAbsolutePath());

        if (file.isDirectory()) {
            Model[] files = getSubFile(file);
            Model[] directories = getSubDirectory(file);


            request.setAttribute("files", files);
            request.setAttribute("directories", directories);


            request.getRequestDispatcher("test.jsp").forward(request, response);
        }else {
            downloadFile(response, file);
        }
    }

    private void downloadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/html");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        try (OutputStream out = resp.getOutputStream();
             FileInputStream in = new FileInputStream(file);) {

            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Model[] getSubDirectory(File file) {
        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory)
                .map(x -> new Model(x, x.length(), new Date(x.lastModified())))
                .toArray(Model[]::new);
    }

    private Model[] getSubFile(File file) {
        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isFile)
                .map(x -> new Model(x, x.length(), new Date(x.lastModified())))
                .toArray(Model[]::new);
    }

    private File readFile(String path) {
        return new File(path);
    }
}
