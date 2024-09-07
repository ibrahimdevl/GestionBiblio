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
import models.Librarian;
import models.Member;
import models.Admin;
import models.User;
import utils.PasswordHashingUtil;

import java.io.IOException;
import java.sql.*;

public class LoginController implements ControllerMethods {
    //torbet fxml bel java
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

    //ki tenzel entree yenzel 3la bouton connecter
    @FXML
    public void click(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
    }

    //bouton annuler tsaker el application
    @FXML
    protected void onCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //ythabet fel les champs m3abin wala le
    @FXML
    protected void onLoginButtonClick() {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Veuillez remplir tous les champs!");
        }
    }

    //ythabet chnowa naw3 el user
    public void validateLogin() {
        Connection connectDB = DatabaseConnection.getConnection(); //yconnecty lel base donnée
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String verifyUsername = "SELECT COUNT(1) FROM `user` WHERE `username`='" + username + "';";//requete bech tlawej 3al user mawjoud wala le

        try {
            Statement statement = connectDB.createStatement();
            ResultSet loginResult = statement.executeQuery(verifyUsername);//executer el requete
            String hashedPassword = getHashedPasswordByUsername(username, connectDB);
            boolean verifyLogin = PasswordHashingUtil.verifyPassword(password, hashedPassword);
            //check if credentials are valid
            if (loginResult.next()) {
                if (verifyLogin) { //law ken user mawjoud
                    //save user info into an object
                    String checkUserType = "SELECT CASE WHEN a.user_id IS NOT NULL THEN 'admin' WHEN b.user_id IS NOT NULL THEN 'librarians' WHEN ad.user_id IS NOT NULL THEN 'member' ELSE 'user' END AS `table` FROM user u LEFT JOIN admin a ON u.user_id = a.user_id LEFT JOIN librarians b ON u.user_id = b.user_id LEFT JOIN member ad ON u.user_id = ad.user_id WHERE u.username = '" + username + "' AND (a.user_id IS NOT NULL OR b.user_id IS NOT NULL OR ad.user_id IS NOT NULL);";
                    ResultSet userTypeResult = statement.executeQuery(checkUserType);//ya3ref chnowa naw3 el user
                    if (userTypeResult.next()) {
                        if (userTypeResult.getString(1).equals("admin")) {//law ken admin
                            Admin admin = new Admin();//thez les donnees mel base lel variable
                            String adminInfo = "SELECT user.* , admin.departement , admin.email FROM `user`, `admin`WHERE user.user_id=admin.user_id AND user.username = '" + username + "';";
                            ResultSet adminInfoResult = statement.executeQuery(adminInfo);
                            if (adminInfoResult.next()) {
                                admin.createAdminInstance(adminInfoResult);//nasn3ou variable admin fih les info
                                redirectAdminHome(admin);
                            }
                        } else if (userTypeResult.getString(1).equals("librarians")) {
                            Librarian librarian = new Librarian();
                            String biblioInfo = "SELECT user.* , librarians.dateEmbauche , librarians.salary FROM `user`, `librarians` WHERE user.user_id=librarians.user_id AND user.username ='" + username + "';";
                            ResultSet biblioInfoResult = statement.executeQuery(biblioInfo);
                            if (biblioInfoResult.next()) {
                                librarian.createLibrarianInstance(biblioInfoResult);
                                redirectLibrariansHome(librarian);
                            }
                        } else if (userTypeResult.getString(1).equals("member")) {
                            Member member = new Member();
                            String memberInfo = "SELECT user.* , member.cin , member.registration_date FROM `user`, `member` WHERE user.user_id=member.user_id AND user.username = '" + username + "';";
                            ResultSet memberInfoResult = statement.executeQuery(memberInfo);
                            if (memberInfoResult.next()) {
                                member.createMemberInstance(memberInfoResult);
                                redirectAdherentHome(member);
                            }
                        } else {
                            loginMessageLabel.setText("Connexion invalide. Veuillez réessayer!");
                        }
                    }
                    User user = new User();
                } else {
                    loginMessageLabel.setText("Connexion invalide. Veuillez réessayer!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //thezek lel page admin
    public void redirectAdminHome(Admin admin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/adminHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        AdminHomeController adminHomeController = fxmlLoader.getController();
        adminHomeController.admin=admin;
        adminHomeController.displayName(admin.getLastName(), admin.getFirstName());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    //thezek lel page biblio
    public void redirectLibrariansHome(Librarian librarian) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/biblioHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        LibrarianHomeController librarianHomeController = fxmlLoader.getController();
        librarianHomeController.librarian = librarian;
        librarianHomeController.displayName(librarian.getLastName(), librarian.getFirstName());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    //thezek lel page member
    public void redirectAdherentHome(Member member) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/adherentHome.fxml"));

        stage = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 500);

        MemberHomeController memberHomeController = fxmlLoader.getController();
        memberHomeController.member = member;
        memberHomeController.displayName(member.getLastName(), member.getFirstName());

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    private String getHashedPasswordByUsername(String username, Connection connection) {
        String SELECT_HASHED_PASSWORD = "SELECT password FROM `user` WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HASHED_PASSWORD)) {

            preparedStatement.setString(1, username); // Bind the username to the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("motDePasse");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if the user does not exist or there was an error
    }
}
