package com.example.servlet;


import com.example.servlet.model.User;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final UserRepository repository = HibernateUserRepo.getRepository();
    private final Map<String, User> sessionList = new HashMap<>();
    private static UserService service;

    private UserService() {
    }

    public static UserService getService() {
        if (service == null) {
            service = new UserService();
        }
        return service;
    }

    public void register(User user) {
        validateEmail(user.getEmail());
        repository.save(user);
    }

    private void validateEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public boolean loginUser(User userCredentials, String session) {
        User user = repository.find(userCredentials.getLogin());
        if (user == null || !user.getPassword().equals(userCredentials.getPassword())) {
            return false;
        }
        sessionList.put(session, user);
        return true;
    }

    public void removeSession(String session) {
        sessionList.remove(session);
    }

    public User getUserFromCookie(String session) {
        return sessionList.get(session);
    }

}