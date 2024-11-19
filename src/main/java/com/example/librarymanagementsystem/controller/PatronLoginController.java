package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.service.PatronService;
import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Optional;

public class PatronLoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField libraryIdField;
    @FXML
    private Button loginButton;

    private PatronService patronService;

    public void initialize() {
        patronService = new PatronService(); // You can use dependency injection or a singleton pattern for this.
        loginButton.setOnAction(e -> authenticatePatron());
    }

    private void authenticatePatron() {
        String username = usernameField.getText();
        String libraryId = libraryIdField.getText();

        Optional<?> patron = patronService.getPatronById(libraryId);
        if (patron.isPresent()) {
            Patron patronValue= (Patron) patron.get();
            if (patronValue.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
                ViewLoader.load("DashboardView.fxml", "Dashboard");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Library ID!");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Library Id not found!");
            System.out.println("Library Id not found!");
        }
    }
}
