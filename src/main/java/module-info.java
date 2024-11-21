module com.example.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.librarymanagementsystem to javafx.fxml;
    exports com.example.librarymanagementsystem;

    exports com.example.librarymanagementsystem.controller;
    opens com.example.librarymanagementsystem.controller to javafx.fxml;

    exports com.example.librarymanagementsystem.model;
    opens com.example.librarymanagementsystem.model to javafx.fxml;

    exports com.example.librarymanagementsystem.enums;
    opens com.example.librarymanagementsystem.enums to javafx.fxml;
}