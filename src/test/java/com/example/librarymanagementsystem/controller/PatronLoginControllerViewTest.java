package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.daos.PatronDAO;
import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.service.PatronService;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

/**
 * The type Patron login controller integration test.
 */
class PatronLoginControllerIntegrationTest {

    private PatronLoginControllerView controller;
    private PatronDAO patronDAO;
    private PatronService patronService;
    private Patron patron;

    private TextField usernameField;
    private TextField libraryIdField;
    private Button loginButton;
    private Label errorLabel;

    /**
     * Init toolkit.
     */
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});

        // Set up test database
        DatabaseConnection.setTestMode(true);
        Connection connection = DatabaseConnection.getConnection();
        DatabaseConnectionTest.setupSchema(connection);
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        // Reset database before each test
        Connection connection = DatabaseConnection.getConnection();
        DatabaseConnectionTest.setupSchema(connection);

        // Use real implementations for integration testing
        patronDAO = new PatronDAO();
        patronService = new PatronService(patronDAO);

        // Initialize controller with real DAO
        controller = new PatronLoginControllerView(patronDAO);
        controller.patronService = patronService;

        // Initialize UI components
        usernameField = new TextField();
        libraryIdField = new TextField();
        loginButton = new Button();
        errorLabel = new Label();

        controller.usernameField = usernameField;
        controller.libraryIdField = libraryIdField;
        controller.loginButton = loginButton;
        controller.errorLabel = errorLabel;

        // Prepare test patron in the database
        patron = new Patron.PatronBuilder()
                .patronLibraryId("LIB-12345678-42")
                .username("johndoe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .address("123 New Street")
                .build();
        patronDAO.addPatron(patron);
    }

    /**
     * Test invalid library id format.
     */
    @Test
    @DisplayName("Invalid Library ID Format Integration Test")
    void testInvalidLibraryIdFormat() {
        // Arrange
        usernameField.setText("johndoe");
        libraryIdField.setText("INVALID-ID");

        // Act
        controller.authenticatePatron();

        // Assert
        assertEquals("Invalid Library ID format! Expected format: LIB-12345678-90", errorLabel.getText());
    }

    /**
     * Test library id not found.
     */
    @Test
    @DisplayName("Library ID Not Found Integration Test")
    void testLibraryIdNotFound() {
        // Arrange
        usernameField.setText("johndoe");
        libraryIdField.setText("LIB-00000000-00");

        // Act
        controller.authenticatePatron();

        // Assert
        assertEquals("Library Id not found!", errorLabel.getText());
    }

    /**
     * Test invalid library id formats.
     *
     * @param libraryId the library id
     */
    @ParameterizedTest
    @CsvSource({
            "12345678-90",
            "LIB-ABCDEFGH-90",
            "LIB-12345678"
    })
    @DisplayName("Parameterized Invalid Library ID Formats Integration Test")
    void testInvalidLibraryIdFormats(String libraryId) {
        // Arrange
        libraryIdField.setText(libraryId);
        usernameField.setText("johndoe");

        // Act
        controller.authenticatePatron();

        // Assert
        assertEquals("Invalid Library ID format! Expected format: LIB-12345678-90", errorLabel.getText());
    }

    /**
     * Test unexpected exception handling.
     */
    @Test
    @DisplayName("Unexpected Exception Handling Integration Test")
    void testUnexpectedExceptionHandling() {
        // Spy on the real PatronService
        PatronService spyPatronService = spy(patronService);
        controller.patronService = spyPatronService;

        // Arrange
        usernameField.setText("johndoe");
        libraryIdField.setText("LIB-12345678-42");

        // Force an exception during getPatronById
        doThrow(new RuntimeException("Simulated database error"))
                .when(spyPatronService)
                .getPatronById("LIB-12345678-42");

        // Act
        controller.authenticatePatron();

        // Assert
        assertEquals("An unexpected error occurred. Please try again.", errorLabel.getText());
    }
}