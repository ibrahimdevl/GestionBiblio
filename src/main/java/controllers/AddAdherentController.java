package controllers;

import databaseConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.PasswordHashingUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddAdherentController implements Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String userQuery = null, adherentQuery = null;
    PreparedStatement preparedStatement;
    int adherentId;
    Date dateInscription;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField cinTextField;
    @FXML
    private TextField addresseTextField;
    @FXML
    private TextField telTextField;

    private boolean update;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent e) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String cin = cinTextField.getText();
        String tel = telTextField.getText();
        if (username.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || tel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez Remplir Toutes LES DONNÉES necessaires");
            alert.showAndWait();
        } else {
            try {
                getQuery();
                insert();
                clean();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clean() {
        usernameTextField.setText(null);
        passwordTextField.setText(null);
        nomTextField.setText(null);
        prenomTextField.setText(null);
        cinTextField.setText(null);
        addresseTextField.setText(null);
        telTextField.setText(null);
    }

    private void getQuery(){
        if (!update) {
            userQuery = "INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES (?, ?, ?, ?, ?, ?);";
            adherentQuery = "INSERT INTO `adherent` (`idUtlstr`,`cin`,`dateInscription`) VALUES (LAST_INSERT_ID(),?,?)";
            dateInscription = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        } else {
            userQuery = "UPDATE `utilisateur` SET `nomUtlstr`=?,`motDePasse`=?,`nom`=?,`prenom`=?,`addresse`=?,`tel`= ? WHERE idUtlstr = '" + adherentId + "'; ";
            adherentQuery = "UPDATE `adherent` SET `cin`=?,`dateInscription`=? WHERE idUtlstr = '" + adherentId + "';";
        }
    }


    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, PasswordHashingUtil.hashPassword(passwordTextField.getText()));
            preparedStatement.setString(3, nomTextField.getText());
            preparedStatement.setString(4, prenomTextField.getText());
            preparedStatement.setString(5, addresseTextField.getText());
            preparedStatement.setInt(6, Integer.parseInt(telTextField.getText()));
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(adherentQuery);
            preparedStatement.setInt(1, Integer.parseInt(cinTextField.getText()));
            preparedStatement.setDate(2, dateInscription);
            preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void setTextField(int id, String username, String password, String nom, String prenom, String address, int tel, int cin, Date date) {
        adherentId = id;
        dateInscription = date;
        usernameTextField.setText(username);
        passwordTextField.setText(password);
        nomTextField.setText(nom);
        prenomTextField.setText(prenom);
        cinTextField.setText(String.valueOf(cin));
        addresseTextField.setText(address);
        telTextField.setText(String.valueOf(tel));
    }

    void setUpdate() {
        this.update = true;

    }
}
