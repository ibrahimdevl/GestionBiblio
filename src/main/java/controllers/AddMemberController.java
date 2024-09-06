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

public class AddMemberController implements Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String userQuery = null, memberQuery = null;
    PreparedStatement preparedStatement;
    int memberId;
    Date registrationDate;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField idNumberTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;

    private boolean update;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent e) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String lastName = lastNameTextField.getText();
        String firstName = firstNameTextField.getText();
        String idNumber = idNumberTextField.getText();
        String phone = phoneTextField.getText();
        if (username.isEmpty() || password.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || idNumber.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields");
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
        lastNameTextField.setText(null);
        firstNameTextField.setText(null);
        idNumberTextField.setText(null);
        addressTextField.setText(null);
        phoneTextField.setText(null);
    }

    private void getQuery(){
        if (!update) {
            userQuery = "INSERT INTO `users` (`username`, `password`, `last_name`, `first_name`, `address`, `phone`) VALUES (?, ?, ?, ?, ?, ?);";
            memberQuery = "INSERT INTO `members` (`user_id`,`id_number`,`registration_date`) VALUES (LAST_INSERT_ID(),?,?)";
            registrationDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        } else {
            userQuery = "UPDATE `users` SET `username`=?,`password`=?,`last_name`=?,`first_name`=?,`address`=?,`phone`= ? WHERE user_id = '" + memberId + "'; ";
            memberQuery = "UPDATE `members` SET `id_number`=?,`registration_date`=? WHERE user_id = '" + memberId + "';";
        }
    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, PasswordHashingUtil.hashPassword(passwordTextField.getText()));
            preparedStatement.setString(3, lastNameTextField.getText());
            preparedStatement.setString(4, firstNameTextField.getText());
            preparedStatement.setString(5, addressTextField.getText());
            preparedStatement.setInt(6, Integer.parseInt(phoneTextField.getText()));
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(memberQuery);
            preparedStatement.setInt(1, Integer.parseInt(idNumberTextField.getText()));
            preparedStatement.setDate(2, registrationDate);
            preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setTextField(int id, String username, String password, String lastName, String firstName, String address, int phone, int idNumber, Date date) {
        memberId = id;
        registrationDate = date;
        usernameTextField.setText(username);
        passwordTextField.setText(password);
        lastNameTextField.setText(lastName);
        firstNameTextField.setText(firstName);
        idNumberTextField.setText(String.valueOf(idNumber));
        addressTextField.setText(address);
        phoneTextField.setText(String.valueOf(phone));
    }

    void setUpdate() {
        this.update = true;
    }
}