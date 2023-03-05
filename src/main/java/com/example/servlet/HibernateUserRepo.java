package com.example.servlet;

import com.example.servlet.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateUserRepo implements UserRepository {
    private static HibernateUserRepo repository;

    public static UserRepository getRepository() {
        if (repository == null) {
            repository = new HibernateUserRepo();
        }

        return repository;
    }

    private HibernateUserRepo() {
    }

    @Override
    public void save(User user) {
        Session session = UserSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User find(Integer id) {
        return (User) UserSession.getSession().openSession().get(User.class, id);
    }

    @Override
    public User find(String login) {
        Session session = UserSession.getSession().openSession();
        User user = (User) session.byNaturalId(User.class).using("login", login).load();
        session.close();
        return user;
    }
}