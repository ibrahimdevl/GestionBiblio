package controllers;

import databaseConnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController implements ControllerMethods {
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField usernameTextField;

    private Stage stage;
    private Scene scene;

    //pressing enter to login just like the button
    @FXML
    public void click(KeyEvent e) {

        if (e.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
    }

    //closing the app when clicking the button
    @FXML
    protected void onCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //check if fields are empty otherwise login
    @FXML
    protected void onLoginButtonClick() {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Veuillez remplir tous les champs!");
        }

    }

    public void validateLogin() {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM `utilisateur` WHERE `nomUtlstr`='" + usernameTextField.getText() + "' AND `motDePasse`='" + passwordPasswordField.getText() + "';";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            //check if credentials are valid
            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    //save user info into an object
                    String adminInfo = "SELECT * FROM `utilisateur` WHERE `nomUtlstr`='" + usernameTextField.getText() + "' AND `motDePasse`='" + passwordPasswordField.getText() + "';";
                    ResultSet adminInfoResult = statement.executeQuery(adminInfo);
                    Utilisateur user = new Utilisateur();
                    if (adminInfoResult.next()) {
                        user.setIdUtlstr(adminInfoResult.getInt(1));
                        user.setNomUtlstr(adminInfoResult.getString(2));
                        user.setMotDePasse(adminInfoResult.getString(3));
                        user.setNom(adminInfoResult.getString(4));
                        user.setPrenom(adminInfoResult.getString(5));
                        user.setAddress(adminInfoResult.getString(6));
                        user.setNumTel(adminInfoResult.getInt(7));
                    }
                    redirectAdminHome(user);

                } else {
                    loginMessageLabel.setText("Connexion invalide. Veuillez réessayer!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //go to the adminHome page after login
    public void redirectAdminHome(Utilisateur user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/adminHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 650, 500);

        AdminHomeController adminHomeController = fxmlLoader.getController();
        adminHomeController.utilisateur = user;
        adminHomeController.displayName(user.getNom(), user.getPrenom());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }


}