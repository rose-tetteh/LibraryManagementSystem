package com.example.librarymanagementsystem.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static boolean isTestMode = false;
    private static Connection testConnection;

    public static void setTestMode(boolean testMode) {
        isTestMode = testMode;
    }

    /**
     * Get connection.
     * If in testMode; Create H2 in-memory test connection
     * @return the connection
     */
    public static Connection getConnection(){
        try{
            if (isTestMode) {
                if (testConnection != null && !testConnection.isClosed()) {
                    return testConnection;
                }

                String url = dotenv.get("H2DB_URL");
                String username = dotenv.get("H2DB_USERNAME");
                String password = dotenv.get("H2DB_PASSWORD");
                testConnection = DriverManager.getConnection(url, username, password);
                return testConnection;
            }
            String url = dotenv.get("DATABASE_URL");
            String username = dotenv.get("DATABASE_USERNAME");
            String password = dotenv.get("DATABASE_PASSWORD");

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database" + e.getMessage(), e);
        }
    }

    public static void closeTestConnection() {
        if (isTestMode && testConnection != null) {
            try {
                testConnection.close();
                testConnection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close test connection", e);
            }
        }
    }
}
