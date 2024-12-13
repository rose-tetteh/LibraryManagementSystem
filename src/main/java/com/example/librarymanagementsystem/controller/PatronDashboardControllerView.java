package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class PatronDashboardControllerView{

    @FXML public Button bookView;
    @FXML
    public Circle profileImage;
    public Button transactionView;
    public Button reservationView;
    @FXML private Label totalBooksLabel;
    @FXML private Label activePatronsLabel;
    @FXML private Label activeTransactionsLabel;
    @FXML private Label pendingReservationsLabel;

    @FXML private HBox dashBox;

    @FXML
    private void handleDashboardView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-dashboard-view.fxml","Patron Dashboard", stage);
        } catch (Exception e) {
            showError("Error loading Dashboard view", e);
        }
    }

//    @FXML
//    private void handleBookView() {
//        try {
//            Stage stage = (Stage) dashBox.getScene().getWindow();
//            ViewLoader.load("/com/example/librarymanagementsystem/book-view.fxml","Books", stage);
//        } catch (Exception e) {
//            showError("Error loading Book view", e);
//            e.printStackTrace(); // Add this to see more detailed error information
//        }
//    }

    @FXML
    private void handleBookView(ActionEvent event) {
        try {
//            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("book-view1.fxml");
        } catch (Exception e) {
            showError("Error loading Book view", e);
            e.printStackTrace(); // Add this to see more detailed error information
        }
    }


    @FXML
    private void handleTransactionView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
           ViewLoader.load("patron-transaction-view.fxml","Approved Transactions", stage);
        } catch (Exception e) {
            showError("Error loading Transaction view", e);
        }
    }

    @FXML
    private void handleReservationView(ActionEvent event) {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-reservation-view.fxml","Reservations", stage);
        } catch (Exception e) {
            showError("Error loading Reservation view", e);
        }
    }

    private void showError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
