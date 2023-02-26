package com.example.servlet;

import com.example.servlet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService service = UserService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null) {
            User user = new User(login, password);
            if (!service.loginUser(user, req.getSession().getId())) {
                req.setAttribute("errorMessage", "Wrong login or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
            resp.sendRedirect("/");
        } else {
            req.setAttribute("errorMessage", "Credentials should not be empty");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}