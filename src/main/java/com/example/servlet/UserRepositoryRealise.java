package com.example.servlet;


import com.example.servlet.model.User;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryRealise implements UserRepository {
    private static UserRepository repository;
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getLogin());
    }

    @Override
    public User find(String login) {
        return users.get(login);
    }

    @Override
    public User getUserFromCookie(Cookie[] cookies) {
        String login = null;
        String password = null;
        String email = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) {
                    login = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
                if (cookie.getName().equals("email")) {
                    email = cookie.getValue();
                }
            }
            if (login != null && password != null && email != null) {
                return new User(login, password, email);
            }
        }

        return null;
    }

    private UserRepositoryRealise() {}

    public static UserRepository getUserRepository() {
        if (repository == null) {
            repository = new UserRepositoryRealise();
        }

        return repository;
    }
}