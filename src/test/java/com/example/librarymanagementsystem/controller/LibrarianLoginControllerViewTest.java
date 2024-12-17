package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.daos.LibrarianDAO;
import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.service.LibrarianService;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibrarianLoginControllerIntegrationTest {

    private LibrarianLoginControllerView controller;
    private LibrarianDAO librarianDAO;
    private LibrarianService librarianService;
    private Librarian librarian;

    private TextField emailField;
    private TextField passwordField;
    private Button loginButton;
    private Label errorLabel;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});

        // Set up test database
        DatabaseConnection.setTestMode(true);
        Connection connection = DatabaseConnection.getConnection();
        DatabaseConnectionTest.setupSchema(connection);
    }

    @BeforeEach
    void setUp() {
        // Reset database before each test
        Connection connection = DatabaseConnection.getConnection();
        DatabaseConnectionTest.setupSchema(connection);

        // Use real implementations for integration testing
        librarianDAO = new LibrarianDAO();
        librarianService = new LibrarianService(librarianDAO);

        // Initialize controller with real DAO
        controller = new LibrarianLoginControllerView(librarianDAO);
        controller.librarianService = librarianService;

        // Initialize UI components
        emailField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button();
        errorLabel = new Label();

        controller.emailField = emailField;
        controller.passwordField = passwordField;
        controller.loginButton = loginButton;
        controller.errorLabel = errorLabel;

        // Prepare test librarian in the database
        librarian = new Librarian.LibrarianBuilder()
                .email("admin@library.com")
                .password("adminPassword123")
                .username("John")
                .phoneNumber("0244335667")
                .build();
        librarianDAO.addLibrarian(librarian);
    }

    @Test
    @DisplayName("Invalid Email Format Integration Test")
    void testInvalidEmailFormat() {
        // Arrange
        emailField.setText("invalidemail");
        passwordField.setText("anypassword");

        // Act
        controller.authenticateLibrarian();

        // Assert
        assertEquals("Invalid email format! Expected format: abcd@sfghd.com", errorLabel.getText());
    }

    @ParameterizedTest
    @CsvSource({
            "test@",
            "test@domain",
            "@domain.com",
            "test@.com"
    })
    @DisplayName("Parameterized Invalid Email Formats Integration Test")
    void testInvalidEmailFormats(String email) {
        // Arrange
        emailField.setText(email);
        passwordField.setText("anypassword");

        // Act
        controller.authenticateLibrarian();

        // Assert
        assertEquals("Invalid email format! Expected format: abcd@sfghd.com", errorLabel.getText());
    }

    @Test
    @DisplayName("Email Not Found Integration Test")
    void testEmailNotFound() {
        // Arrange
        emailField.setText("notfound@library.com");
        passwordField.setText("anypassword");

        // Act
        controller.authenticateLibrarian();

        // Assert
        assertEquals("User with email not found!", errorLabel.getText());
    }
}