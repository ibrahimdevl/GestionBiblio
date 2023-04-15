module GestionBiblio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens application;
    opens controllers;
}