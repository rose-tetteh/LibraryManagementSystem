package com.example.librarymanagementsystem.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseConnectionTest {
    private static final String SCHEMA_SCRIPT = "/db/h2-schema.sql";
//    private static final String DROP_TABLES_SCRIPT = "/h2-drop-tables.sql";

    /**
     * Sets up the database schema from a SQL script.
     * Split the script into individual statements.
     * @param connection the database connection.
     */
    public static void setupSchema(Connection connection) {
        try {
            String schemaScript = readScriptFile();

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP ALL OBJECTS");
                String[] statements = schemaScript.split(";");

                for (String statement : statements) {
                    if (!statement.trim().isEmpty()) {
                        stmt.execute(statement.trim());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup test database schema", e);
        }
    }

//    /**
//     * Tears down the database schema by dropping all tables.
//     *
//     * @param connection the database connection
//     */
//    public static void tearDownSchema(Connection connection) {
//        try {
//            String dropTablesScript = readScriptFile(DROP_TABLES_SCRIPT);
//
//            try (Statement stmt = connection.createStatement()) {
//                // Split the script into individual statements
//                String[] statements = dropTablesScript.split(";");
//
//                for (String statement : statements) {
//                    if (!statement.trim().isEmpty()) {
//                        stmt.execute(statement.trim());
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to tear down test database schema", e);
//        }
//    }

    /**
     * Reads a SQL script from the classpath.
     *
     * @return the contents of the script as a string
     */
    private static String readScriptFile() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(DatabaseConnectionTest.class.getResourceAsStream(DatabaseConnectionTest.SCHEMA_SCRIPT))))) {

            return reader.lines()
                    .filter(line -> !line.trim().startsWith("--") && !line.trim().isEmpty())
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read SQL script: " + DatabaseConnectionTest.SCHEMA_SCRIPT, e);
        }
    }
}