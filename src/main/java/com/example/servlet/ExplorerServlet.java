package com.example.servlet;

import com.example.servlet.model.Model;
import com.example.servlet.model.User;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@WebServlet(urlPatterns = {"/"})
public class ExplorerServlet extends HttpServlet {
    private final ExplorerService explorerService = new ExplorerService();
    private final UserService userService = UserService.getService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")));

        User user = userService.getUserFromCookie(request.getSession().getId());

        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        String pathVariable = request.getParameter("path");
        File file = explorerService.getUserFiles(user.getLogin(), pathVariable);

        request.setAttribute("path", file.toPath());

        if (file.isDirectory()) {
            showExplorer(file, request, response);
        } else {
            downloadFile(response, file);
        }
    }

    private void showExplorer(File file, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model[] files = explorerService.getSubFile(file);
        Model[] directories = explorerService.getSubDirectory(file);

        request.setAttribute("files", files);
        request.setAttribute("directories", directories);

        request.getRequestDispatcher("test.jsp").forward(request, response);
    }

    private void downloadFile(HttpServletResponse resp, File file) {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        userService.removeSession(req.getSession().getId());
        for (Cookie cookie : cookies) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/login");
    }
}
