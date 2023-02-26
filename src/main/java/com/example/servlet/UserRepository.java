package com.example.servlet;

import com.example.servlet.model.User;

import javax.servlet.http.Cookie;

public interface UserRepository {
    void save(User user);
    void delete(User user);
    User find(String login);
    User getUserFromCookie(Cookie[] cookies);
}