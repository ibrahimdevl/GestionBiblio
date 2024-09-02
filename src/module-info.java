module GestionBiblio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens application;
    opens controllers;
    opens models to javafx.base;
}