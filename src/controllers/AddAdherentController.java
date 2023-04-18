package controllers;

import databaseConnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Adherent;

import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddAdherentController implements Initializable {

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
    @FXML
    private Button saveButton;
    @FXML
    private Button cleanButton;


    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();

    String query = null;
    String userQuery = null,adherentQuery=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Adherent adherent = null;
    private boolean update;
    int adherentId;
    Date dateInscription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(){

        String username=usernameTextField.getText();
        String password= passwordTextField.getText();
        String nom= nomTextField.getText();
        String prenom = prenomTextField.getText();
        String cin = cinTextField.getText();
        String addresse = addresseTextField.getText();
        String tel = telTextField.getText();


        try{
            userQuery ="INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES (?, ?, ?, ?, ?, ?);";

            adherentQuery="INSERT INTO `adherent` (`idUtlstr`,`cin`,`dateInscription`) VALUES (LAST_INSERT_ID(),?,?)";
            preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, passwordTextField.getText());
            preparedStatement.setString(3, nomTextField.getText());
            preparedStatement.setString(4, prenomTextField.getText());
            preparedStatement.setString(5, addresseTextField.getText());
            preparedStatement.setInt(6, Integer.parseInt(telTextField.getText()));
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(adherentQuery);
            preparedStatement.setInt(1, Integer.parseInt(cinTextField.getText()));
            preparedStatement.setDate(2, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*if (username.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty()|| cin.isEmpty()|| tel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez Remplir Toutes LES DONNÉES necessaires");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();
        }*/

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

    private void getQuery() throws SQLException {
        if (update == false) {
            query = "INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`) VALUES (?,?,?,?,?,?); " +
                    "INSERT INTO `adherent` (idUtlstr, cin, dateInscription) VALUES (LAST_INSERT_ID(), ?, ?);";

            userQuery ="INSERT INTO `utilisateur` (`nomUtlstr`, `motDePasse`, `nom`, `prenom`, `addresse`, `tel`)\n" +
                    "VALUES ('test', 'Test1234', 'Mohame', 'Hlfvfel', 'Raoued', '54385290')";
            Statement statement = connection.createStatement();
            statement.executeQuery(userQuery);
            //dateInscription=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        } else {
            query = "UPDATE `utilisateur` SET `nomUtlstr`=?,`motDePasse`=?,`nom`=?,`prenom`=?,`addresse`=?,`tel`= ? WHERE id = '"+adherentId+"'; " +
                    "UPDATE `adherent` SET `cin`=?,`dateInscription`=? WHERE id = '"+adherentId+"';";
        }
    }



    private void insert() {
        try {
            /*preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, passwordTextField.getText());
            preparedStatement.setString(3, nomTextField.getText());
            preparedStatement.setString(4, prenomTextField.getText());
            preparedStatement.setString(5, addresseTextField.getText());
            preparedStatement.setInt(6, Integer.parseInt(telTextField.getText()));
            preparedStatement.setInt(7, Integer.parseInt(cinTextField.getText()));
            preparedStatement.setDate(8, dateInscription);
            preparedStatement.execute();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void setTextField(int id, String username, String password, String nom, String prenom,  String address, int tel,int cin, Date date) {

        adherentId = id;
        dateInscription=date;
        usernameTextField.setText(username);
        passwordTextField.setText(password);
        nomTextField.setText(nom);
        prenomTextField.setText(prenom);
        cinTextField.setText(String.valueOf( cin));
        addresseTextField.setText(address);
        telTextField.setText(String.valueOf(tel));

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}
