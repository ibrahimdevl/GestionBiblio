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
import models.Adherent;
import models.Admin;
import models.Bibliothecaire;
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

    //pressing enter to log in just like the button
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
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String verifyLogin = "SELECT COUNT(1) FROM `utilisateur` WHERE `nomUtlstr`='" + username + "' AND `motDePasse`='" + password + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet loginResult = statement.executeQuery(verifyLogin);
            //check if credentials are valid
            if (loginResult.next()) {
                if (loginResult.getInt(1) == 1) {
                    //save user info into an object
                    String checkUserType = "SELECT CASE WHEN a.idUtlstr IS NOT NULL THEN 'admin' WHEN b.idUtlstr IS NOT NULL THEN 'bibliothecaire' WHEN ad.idUtlstr IS NOT NULL THEN 'adherent' ELSE 'utilisateur' END AS `table` FROM utilisateur u LEFT JOIN admin a ON u.idUtlstr = a.idUtlstr LEFT JOIN bibliothecaire b ON u.idUtlstr = b.idUtlstr LEFT JOIN adherent ad ON u.idUtlstr = ad.idUtlstr WHERE u.nomUtlstr = '" + username + "' AND u.motDePasse = '" + password + "' AND (a.idUtlstr IS NOT NULL OR b.idUtlstr IS NOT NULL OR ad.idUtlstr IS NOT NULL);";
                    ResultSet userTypeResult = statement.executeQuery(checkUserType);
                    if (userTypeResult.next()) {
                        if (userTypeResult.getString(1).equals("admin")) {
                            Admin admin = new Admin();
                            String adminInfo = "SELECT utilisateur.* , admin.departement , admin.email FROM `utilisateur`, `admin`WHERE utilisateur.idUtlstr=admin.idUtlstr AND utilisateur.nomUtlstr = '" + username + "' AND utilisateur.motDePasse='" + password + "';";
                            ResultSet adminInfoResult = statement.executeQuery(adminInfo);
                            if (adminInfoResult.next()) {
                                admin.createAdminInstance(adminInfoResult);
                                redirectAdminHome(admin);
                            }
                        } else if (userTypeResult.getString(1).equals("bibliothecaire")) {
                            Bibliothecaire bibliothecaire = new Bibliothecaire();
                            String biblioInfo = "SELECT utilisateur.* , bibliothecaire.dateEmbauche , bibliothecaire.salaire FROM `utilisateur`, `bibliothecaire` WHERE utilisateur.idUtlstr=bibliothecaire.idUtlstr AND utilisateur.nomUtlstr ='" + username + "' AND utilisateur.motDePasse='" + password + "';";
                            ResultSet biblioInfoResult = statement.executeQuery(biblioInfo);
                            if (biblioInfoResult.next()) {
                                bibliothecaire.createBibliothecaireInstance(biblioInfoResult);
                                redirectBibliothecaireHome(bibliothecaire);
                            }
                        } else if (userTypeResult.getString(1).equals("adherent")) {
                            Adherent adherent = new Adherent();
                            String adherentInfo = "SELECT utilisateur.* , adherent.cin , adherent.dateInscription FROM `utilisateur`, `adherent` WHERE utilisateur.idUtlstr=adherent.idUtlstr AND utilisateur.nomUtlstr = '" + username + "' AND utilisateur.motDePasse='" + password + "';";
                            ResultSet adherentInfoResult = statement.executeQuery(adherentInfo);
                            if (adherentInfoResult.next()) {
                                adherent.createAdherentInstance(adherentInfoResult);
                                redirectAdherentHome(adherent);
                            }
                        } else {
                            loginMessageLabel.setText("Connexion invalide. Veuillez réessayer!");
                        }
                    }
                    Utilisateur user = new Utilisateur();
                } else {
                    loginMessageLabel.setText("Connexion invalide. Veuillez réessayer!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //go to the adminHome page after login
    public void redirectAdminHome(Admin admin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/adminHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        AdminHomeController adminHomeController = fxmlLoader.getController();
        adminHomeController.admin=admin;
        adminHomeController.displayName(admin.getNom(), admin.getPrenom());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    //go to the biblioHome page after login
    public void redirectBibliothecaireHome(Bibliothecaire bibliothecaire) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/biblioHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        BiblioHomeController biblioHomeController = fxmlLoader.getController();
        biblioHomeController.bibliothecaire=bibliothecaire;
        biblioHomeController.displayName(bibliothecaire.getNom(), bibliothecaire.getPrenom());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    //go to the adherentHome page after login
    public void redirectAdherentHome(Adherent adherent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/adherentHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        AdherentHomeController adherentHomeController = fxmlLoader.getController();
        adherentHomeController.adherent = adherent;
        adherentHomeController.displayName(adherent.getNom(), adherent.getPrenom());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }
}