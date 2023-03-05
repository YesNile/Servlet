package com.example.servlet;


import com.example.servlet.model.User;

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
    public User find(Integer id) {
        User user = null;
        for (User userDto : users.values()) {
            if (userDto.getId() == id) {
                user = userDto;
                break;
            }
        }
        return user;
    }

    @Override
    public User find(String login) {
        return users.get(login);
    }

    private UserRepositoryRealise() {
    }

    public static UserRepository getRepository() {
        if (repository == null) {
            repository = new UserRepositoryRealise();
        }

        return repository;
    }
}