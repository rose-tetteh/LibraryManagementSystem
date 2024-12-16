package com.example.librarymanagementsystem.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest {
    /**
     * Sets schema.
     *
     * @param connection the connection
     */
    public static void setupSchema(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            // Create Patron table for testing
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS Patron (" +
                            "patronId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                            "patronLibraryId VARCHAR(255) UNIQUE NOT NULL, " +
                            "username VARCHAR(255) UNIQUE NOT NULL, " +
                            "email VARCHAR(255) UNIQUE NOT NULL, " +
                            "phoneNumber VARCHAR(255) NOT NULL, " +
                            "address VARCHAR(255) NOT NULL" +
                            ")"
            );


        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup test database schema", e);
        }
    }

    /**
     * Tear down schema.
     *
     * @param connection the connection
     */
    public static void tearDownSchema(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Patron");
            stmt.execute("DROP TABLE Patron");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to tear down test database schema", e);
        }
    }
}