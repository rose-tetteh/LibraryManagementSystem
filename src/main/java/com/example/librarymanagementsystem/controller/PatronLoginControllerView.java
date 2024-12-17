package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.daos.PatronDAO;
import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.service.PatronService;
import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class PatronLoginControllerView {

    @FXML
    protected TextField usernameField;
    @FXML
    protected TextField libraryIdField;
    @FXML
    protected Button loginButton;
    @FXML
    protected Label errorLabel;

    protected PatronService patronService;
    private final PatronDAO patronDAO;

    private static final String LIBRARY_ID_PATTERN = "LIB-\\d{8}-\\d{2}";

    public PatronLoginControllerView(PatronDAO patronDAO) {
        this.patronDAO = patronDAO;
    }

    public void initialize() {
        patronService = new PatronService(patronDAO); // You can use dependency injection or a singleton pattern for this.
        loginButton.setOnAction(e -> authenticatePatron());
    }

    public void authenticatePatron() {
        String username = usernameField.getText().trim();
        String libraryId = libraryIdField.getText().trim();

        if (!isValidLibraryId(libraryId)) {
            errorLabel.setText("Invalid Library ID format! Expected format: LIB-12345678-90");
            return;
        }

        try {
            Optional<Patron> patron = patronService.getPatronById(libraryId);
            if (patron.isPresent()) {
                Patron patronValue= patron.get();
                if (patronValue.getUsername().equals(username)) {
                    errorLabel.setText("Login Successful!");
                    ViewLoader.load("patron-dashboard-view.fxml");
                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    currentStage.close();
                } else {
                    errorLabel.setText("Invalid Username or Library ID!");
                }
            }else {
                errorLabel.setText("Library Id not found!");
            }
        } catch (RuntimeException | IOException e) {
            errorLabel.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error during authentication: " + e.getMessage());
        }
    }

    private boolean isValidLibraryId(String libraryId) {
        return libraryId.matches(LIBRARY_ID_PATTERN);
    }
}
