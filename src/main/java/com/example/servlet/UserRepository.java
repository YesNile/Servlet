package com.example.servlet;

import com.example.servlet.model.User;

import javax.servlet.http.Cookie;

public interface UserRepository {
    void save(User user);
    User find(Integer id);
    User find(String login);
}