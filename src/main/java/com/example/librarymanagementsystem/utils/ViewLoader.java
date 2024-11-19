package com.example.librarymanagementsystem.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {
    public static void load(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource("/com/example/librarymanagementsystem/patron-login-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}
