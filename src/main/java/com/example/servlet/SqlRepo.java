package com.example.servlet;

import com.example.servlet.model.User;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlRepo implements UserRepository{
    private static final String dbUserName = "postgres";
    private static final String dbPassword = "1234";
    private static final String dbUrl = "jdbc:postgresql://localhost:5432/JavaCsu";

    private static Connection connection;

    private static UserRepository repository;

    static {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void save(User user) {
        try{
            String query = "INSERT INTO users (login, password, email) VALUES(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try{
            String query = "DELETE FROM users WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());

            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User find(String login) {
        try{
            String query = "SELECT * FROM users WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();

            result.next();

            int id = result.getInt("id");
            String resultLogin = result.getString("login");
            String pass = result.getString("password");
            String email = result.getString("email");

            return new User(id, resultLogin, pass, email);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserFromCookie(Cookie[] cookies) {
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")){
                    return find(cookie.getValue());
                }
            }
        }
        return null;
    }

    private SqlRepo() {
    }

    public static UserRepository getUserRepository(){
        if (repository == null){
            repository = new SqlRepo();
        }

        return repository;
    }
}