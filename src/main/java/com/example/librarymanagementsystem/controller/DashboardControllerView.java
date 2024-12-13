package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;



public class DashboardControllerView{

    @FXML public Button bookView;
    public Circle profileImage;
    @FXML private Label totalBooksLabel;
    @FXML private Label activePatronsLabel;
    @FXML private Label activeTransactionsLabel;
    @FXML private Label pendingReservationsLabel;

    @FXML private HBox dashBox;

    @FXML
    private void handleDashboardView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("dashboard-view.fxml", "Librarian Dashboard",stage);
        } catch (Exception e) {
            showError("Error loading Dashboard view", e);
        }
    }

    @FXML
    private void handleBookView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("book-view.fxml", "Book Management",stage);
        } catch (Exception e) {
            showError("Error loading Book view", e);
            e.printStackTrace(); // Add this to see more detailed error information
        }
    }

    @FXML
    private void handlePatronView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-view.fxml", "Patron Management",stage);
        } catch (Exception e) {
            showError("Error loading Patron view", e);
        }
    }

    @FXML
    private void handleStaffView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("librarians-view.fxml", "Staff Management",stage);
        } catch (Exception e) {
            showError("Error loading Staff view", e);
        }
    }

    @FXML
    private void handleTransactionView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
           ViewLoader.load("transaction-view.fxml", "Transaction Management",stage);
        } catch (Exception e) {
            showError("Error loading Transaction view", e);
        }
    }

    @FXML
    private void handleReservationView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("reservation-view.fxml", "Reservation Management",stage);
        } catch (Exception e) {
            showError("Error loading Reservation view", e);
        }
    }

    private void showError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
