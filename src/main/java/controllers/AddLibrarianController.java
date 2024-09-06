package controllers;

import databaseConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.PasswordHashingUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AddLibrarianController implements Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String userQuery = null, librarianQuery = null;
    PreparedStatement preparedStatement;
    int librarianId;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private DatePicker hireDatePicker;

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
        String salary = salaryTextField.getText();
        String phone = phoneTextField.getText();
        String hireDate = String.valueOf(hireDatePicker.getValue());
        if (username.isEmpty() || password.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || salary.isEmpty() || phone.isEmpty() || hireDate.isEmpty()) {
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
        salaryTextField.setText(null);
        addressTextField.setText(null);
        phoneTextField.setText(null);
        hireDatePicker.setValue(null);
    }

    private void getQuery() {
        if (!update) {
            userQuery = "INSERT INTO `users` (`username`, `password`, `last_name`, `first_name`, `address`, `phone`) VALUES (?, ?, ?, ?, ?, ?);";
            librarianQuery = "INSERT INTO `librarians` (`user_id`,`hire_date`,`salary`) VALUES (LAST_INSERT_ID(),?,?)";
        } else {
            userQuery = "UPDATE `users` SET `username`=?,`password`=?,`last_name`=?,`first_name`=?,`address`=?,`phone`= ? WHERE user_id = '" + librarianId + "'; ";
            librarianQuery = "UPDATE `librarians` SET `hire_date`=?,`salary`=? WHERE user_id = '" + librarianId + "';";
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
            preparedStatement = connection.prepareStatement(librarianQuery);
            preparedStatement.setDate(1, Date.valueOf(hireDatePicker.getValue()));
            preparedStatement.setFloat(2, Float.parseFloat(salaryTextField.getText()));
            preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setTextField(int id, String username, String password, String lastName, String firstName, String address, int phone, float salary, Date date) {
        librarianId = id;
        usernameTextField.setText(username);
        passwordTextField.setText(password);
        lastNameTextField.setText(lastName);
        firstNameTextField.setText(firstName);
        salaryTextField.setText(String.valueOf(salary));
        addressTextField.setText(address);
        phoneTextField.setText(String.valueOf(phone));
        hireDatePicker.setValue(date.toLocalDate());
    }

    void setUpdate() {
        this.update = true;
    }
}