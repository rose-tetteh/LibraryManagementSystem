package com.example.librarymanagementsystem.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseConnectionTest {
    private static final String SCHEMA_SCRIPT = "/db/h2-schema.sql";

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

    public static void testData(Connection connection) {
        try (var statement = connection.createStatement()) {
            // Insert required library resources
            statement.execute("INSERT INTO libraryresource (title, resourceType, numberOfCopies) \n" +
                    "VALUES ('Book A', 'book', 5)");
            statement.execute("INSERT INTO libraryresource (title, resourceType, numberOfCopies) \n" +
                    "VALUES ('Book B', 'book', 3)");

            statement.execute("INSERT INTO patron (patronLibraryId, username, email, phoneNumber, address) " +
                    "VALUES ('LIB-12345678-42', 'johndoe', 'john.doe@example.com', '1234567890', '123 Main St')");
            statement.execute("INSERT INTO patron (patronLibraryId, username, email, phoneNumber, address) " +
                    "VALUES ('LIB-12345678-45', 'janesmith', 'jane.smith@example.com', '0987654321', '456 Elm St')");
        } catch (Exception e) {
            fail("Test setup failed: Unable to insert library resources" + e.getMessage());
        }
    }
}