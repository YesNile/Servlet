package com.example.servlet;

import com.example.servlet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private final UserService service = UserService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (!Objects.equals(login, "") && !Objects.equals(password, "") && !Objects.equals(email, "")) {
            try {
                User user = new User(login, password, email);
                service.register(user);
                resp.sendRedirect("/login");
            } catch (IllegalArgumentException e) {
                req.setAttribute("errorMessage", e.getMessage());
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("errorMessage", "Credentials should not be empty");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}