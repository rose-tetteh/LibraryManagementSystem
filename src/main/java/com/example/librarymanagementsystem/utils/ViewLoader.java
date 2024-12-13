package com.example.librarymanagementsystem.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {


//    /**
//     * Load.
//     *
//     * @param fxmlPath the fxml path
//     * @param title    the title
//     * Add CSS if needed
//     */
//    public static void load(String fxmlPath, String title) {
//        try {
////            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(fxmlPath));
//            Parent root = FXMLLoader.load(ViewLoader.class.getResource(fxmlPath));
//            Stage stage = new Stage();
//            stage.setTitle(title);
//
//            Scene scene = new Scene(root);
////            String cssPath = fxmlPath.replace(".fxml", ".css");
////            try {
////                scene.getStylesheets().add(ViewLoader.class.getResource(cssPath).toExternalForm());
////            } catch (NullPointerException e) {
////                System.out.println("No CSS file found for: " + cssPath);
////            }
//
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException("Error loading FXML: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Load.
//     *
//     * @param fxmlPath      the fxml path
//     * @param title         the title
//     * @param existingStage the existing stage
//     * Overloaded method to open in existing stage if needed
//     */
    public static void load(String fxmlPath, String title, Stage existingStage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource("/com/example/librarymanagementsystem/" + fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String cssPath = fxmlPath.replace(".fxml", ".css");
            try {
                scene.getStylesheets().add(ViewLoader.class.getResource(cssPath).toExternalForm());
            } catch (NullPointerException e) {
                System.out.println("No CSS file found for: " + cssPath);
            }

            existingStage.setTitle(title);
            existingStage.setScene(scene);
            existingStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML: " + e.getMessage(), e);
        }
    }
public static void load(String fxmlFileName) throws IOException {
    FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource("/com/example/librarymanagementsystem/" + fxmlFileName));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
}
}