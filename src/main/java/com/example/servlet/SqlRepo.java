package com.example.servlet;

import com.example.servlet.UserRepository;
import com.example.servlet.model.User;

import java.sql.*;

public class SqlRepo implements UserRepository {
    private static final String dbUserName = "postgres";
    private static final String dbPassword = "1234";
    private static final String dbUrl = "jdbc:postgresql://localhost:5432/JavaCsu";

    private static Connection connection;
    private static UserRepository repository;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        try {
            String query = "INSERT INTO users (login, password, email) VALUES(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(Integer id) {
        try {
            String query = "SELECT * FROM users WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return createUserFromResultSet(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private User createUserFromResultSet(ResultSet set) throws SQLException {
        set.next();

        int resultId = set.getInt("id");
        String login = set.getString("login");
        String pass = set.getString("password");
        String email = set.getString("email");

        return new User(resultId, login, pass, email);
    }

    @Override
    public User find(String login) {
        try {
            String query = "SELECT * FROM users WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            return createUserFromResultSet(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private SqlRepo() {
    }

    public static UserRepository getRepository() {
        if (repository == null) {
            repository = new SqlRepo();
        }

        return repository;
    }
}