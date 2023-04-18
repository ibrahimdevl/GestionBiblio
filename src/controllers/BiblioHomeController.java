package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Bibliothecaire;
import models.Utilisateur;

import java.io.IOException;

public class BiblioHomeController implements ControllerMethods {

    @FXML private Label welcomeLabel;

    private Stage stage;
    private Scene scene;
    protected Bibliothecaire bibliothecaire;

    //go back to login page
    public void redirectLogin(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(),520,400);

        centerScene(stage,scene);

        stage.setScene(scene);
        stage.show();
    }

    //custom welcome message
    public void displayName(String nom, String prenom){
        welcomeLabel.setText("Bienvenue "+nom+" "+prenom+"!");
    }

}
