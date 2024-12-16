package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.daos.LibrarianDAO;
import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.service.LibrarianService;
import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Optional;

public class LibrarianLoginControllerView {

    @FXML
    private TextField emailField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private Button passwordVisibilityButton;
    @FXML
    private ImageView passwordVisibilityIcon;
    @FXML
    private Image eyeClosedImage;
    @FXML
    private Image eyeOpenImage;

    private LibrarianService librarianService;
    private LibrarianDAO librarianDAO;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public LibrarianLoginControllerView(LibrarianDAO librarianDAO) {
        this.librarianDAO = librarianDAO;
    }

    public void initialize() {
        librarianService = new LibrarianService(librarianDAO);
        visiblePasswordField = new TextField();
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);

        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());

        passwordVisibilityButton.setOnAction(event -> togglePasswordVisibility());
        loginButton.setOnAction(e -> authenticateLibrarian());
    }

    private void authenticateLibrarian() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (!isValidEmail(email)) {
            errorLabel.setText("Invalid email format! Expected format: abcd@sfghd.com");
            return;
        }

        Optional<?> librarian = librarianService.getLibrarianByEmail(email);
        try {
            if (librarian.isPresent()) {
                Librarian librarianValue= (Librarian) librarian.get();
                if (librarianValue.getPassword().equals(password)) {
                    errorLabel.setText("Login Successful!");
                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    currentStage.close();
                    ViewLoader.load("dashboard-view.fxml");
                } else {
                    errorLabel.setText("Invalid Email or Password!");
                }
            }else {
                errorLabel.setText("User with email not found!");
            }
        } catch (Exception e) {
            errorLabel.setText("An unexpected error occurred. Please try again.");
            System.err.println("Error during authentication: " + e.getMessage());
        }
    }

    private void togglePasswordVisibility() {
        boolean isPasswordVisible = visiblePasswordField.isVisible();

        if (isPasswordVisible) {
            // Switch to password field
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            visiblePasswordField.setManaged(false);
            visiblePasswordField.setVisible(false);

            // Update icon
            passwordVisibilityIcon.setImage(eyeClosedImage);
        } else {
            // Switch to visible text field
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setManaged(false);
            visiblePasswordField.setVisible(false);
            passwordField.setManaged(true);
            passwordField.setVisible(true);

            // Update icon
            passwordVisibilityIcon.setImage(eyeOpenImage);
        }
    }

    private boolean isValidEmail(String libraryId) {
        return libraryId.matches(EMAIL_PATTERN);
    }
}
