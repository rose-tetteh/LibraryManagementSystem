package com.example.librarymanagementsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/libraryManagementSys";
    private static final String USERNAME = "postgres ";
    private static final String PASSWORD = "postAdmin";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
