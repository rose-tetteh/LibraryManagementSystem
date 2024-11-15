package com.example.librarymanagementsystem.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection(){
        try{
            String url = dotenv.get("DATABASE_URL");
            String username = dotenv.get("DATABASE_USERNAME");
            String password = dotenv.get("DATABASE_PASSWORD");

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database" + e.getMessage());
        }
    }
}
