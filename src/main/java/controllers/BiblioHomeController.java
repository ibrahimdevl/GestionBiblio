package controllers;

import application.Main;
import databaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class BiblioHomeController implements ControllerMethods, Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Adherent adherent = null;
    Bibliothecaire bibliothecaire;

    ObservableList<Adherent> AdherentList = FXCollections.observableArrayList();

    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField adherentSearchField;
    @FXML
    private TableView<Adherent> adherentTableView;
    @FXML
    private TableColumn<Adherent, String> nomCol;
    @FXML
    private TableColumn<Adherent, String> prenomCol;
    @FXML
    private TableColumn<Adherent, Integer> cinCol;
    @FXML
    private TableView<EmpruntPretData> pretTableView;
    @FXML
    private TableColumn<EmpruntPretData, String> pretDocumentCol;
    @FXML
    private TableColumn<EmpruntPretData, String> pretAdherentCol;
    @FXML
    private TableColumn<EmpruntPretData, Date> pretDatePretCol;

    @FXML
    private TableView<EmpruntPretData> empruntTableView;
    @FXML
    private TableColumn<Document, String> empruntDocumentCol;
    @FXML
    private TableColumn<Document, String> empruntAdherentCol;
    @FXML
    private TableColumn<Document, Date> empruntDateEmpCol;

    ObservableList<EmpruntPretData> pretDataList = FXCollections.observableArrayList();
    ObservableList<EmpruntPretData> empruntDataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadAdherent();
        loadPret();
        loadEmprunt();
    }
    private void loadAdherent() {
        refreshAdherentTable();
        nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        cinCol.setCellValueFactory(new PropertyValueFactory<>("Cin"));
    }

    private void loadPret(){
        refreshPretTable();
        pretDocumentCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        pretAdherentCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        pretDatePretCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadEmprunt(){
        refreshEmpruntTable();
        empruntDocumentCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        empruntAdherentCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        empruntDateEmpCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void refreshAdherentTable() {
        try {
            AdherentList.clear();

            query = "SELECT utilisateur.* , adherent.cin , adherent.dateInscription FROM `utilisateur`, `adherent` WHERE adherent.idUtlstr=utilisateur.idUtlstr";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Adherent adh = new Adherent();
                adh.createAdherentInstance(resultSet);
                AdherentList.add(adh);
                adherentTableView.setItems(AdherentList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    public void refreshPretTable() {
        try {
            pretDataList.clear();
            query = "SELECT pret.idPret as id,pret.idDoc,pret.idAdh,document.titre, CONCAT(utilisateur.nom, ' ', utilisateur.prenom) as nom, pret.datePret as date\n" +
                    "FROM pret\n" +
                    "JOIN document ON pret.idDoc = document.idDoc\n" +
                    "JOIN utilisateur ON pret.idAdh = utilisateur.idUtlstr\n" +
                    "WHERE pret.validee=0";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idPret = resultSet.getInt("id");
                int idDoc = resultSet.getInt("idDoc");
                int idAdh = resultSet.getInt("idAdh");
                String titre = resultSet.getString("titre");
                String nom = resultSet.getString("nom");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                EmpruntPretData empruntPretData = new EmpruntPretData(idPret,idDoc,idAdh,titre, nom, date);
                pretDataList.add(empruntPretData);
                pretTableView.setItems(pretDataList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void refreshEmpruntTable() {
        try {
            empruntDataList.clear();
            query = "SELECT emprunt.idEmprunt as id,emprunt.idDoc,emprunt.idAdh,document.titre, CONCAT(utilisateur.nom, ' ', utilisateur.prenom) as nom, emprunt.dateEmprunt as date\n" +
                    "FROM emprunt\n" +
                    "JOIN document ON emprunt.idDoc = document.idDoc\n" +
                    "JOIN utilisateur ON emprunt.idAdh = utilisateur.idUtlstr\n" +
                    "WHERE emprunt.retourne=0";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idEmprunt = resultSet.getInt("id");
                int idDoc = resultSet.getInt("idDoc");
                int idAdh = resultSet.getInt("idAdh");
                String titre = resultSet.getString("titre");
                String nom = resultSet.getString("nom");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                EmpruntPretData empruntPretData = new EmpruntPretData(idEmprunt,idDoc,idAdh,titre, nom, date);
                empruntDataList.add(empruntPretData);
                empruntTableView.setItems(empruntDataList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void adherentSearchForRows() {
        String searchString = adherentSearchField.getText();
        if (searchString == null || searchString.isEmpty()) {
            adherentTableView.setItems(AdherentList);
        } else {
            ObservableList<Adherent> filteredData = FXCollections.observableArrayList();
            for (Adherent adherent : AdherentList) {
                if (adherent.matchesSearch(searchString)) {
                    filteredData.add(adherent);
                }
            }
            adherentTableView.setItems(filteredData);
        }
    }

    public void adherentEditSelectedRow() {
        adherent = adherentTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addAdherent.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        redirectAdherentEdit(loader, adherent);

    }

    public static void redirectAdherentEdit(FXMLLoader loader, Adherent adherent) {
        AddAdherentController addStudentController = loader.getController();
        addStudentController.setUpdate();
        addStudentController.setTextField(adherent.getIdUtlstr(), adherent.getNomUtlstr(), adherent.getMotDePasse(),
                adherent.getNom(), adherent.getPrenom(), adherent.getAddress(), adherent.getNumTel(), adherent.getCin(),
                adherent.getDateInscription());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public void adherentDeleteSelectedRow() {
        try {
            adherent = adherentTableView.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `utilisateur` WHERE idUtlstr  =" + adherent.getIdUtlstr();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshAdherentTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void adherentAdd() {
        adherent = adherentTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addAdherent.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    // go back to login page
    public void redirectLogin(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    // custom welcome message
    public void displayName(String nom, String prenom) {
        welcomeLabel.setText("Bienvenue " + nom + " " + prenom + "!");
    }

    public void validerPret() {
        EmpruntPretData item = pretTableView.getSelectionModel().getSelectedItem();
        int idAdh = item.idAdhProperty();
        int idDoc = item.idDocProperty();
        int idPret = item.idProperty();

        try {
            query="INSERT INTO `emprunt` (`dateEmprunt`, `dateRetour`, `idAdh`, `idBiblio`, `idDoc`, `retourne`) VALUES (?, ?,?, ?, ?, '0')";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1,new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, 2);
            java.sql.Date twoWeeksFromNow = new java.sql.Date(calendar.getTimeInMillis());
            preparedStatement.setDate(2,twoWeeksFromNow);
            preparedStatement.setInt(3,idAdh);
            preparedStatement.setInt(4,bibliothecaire.getIdUtlstr());
            preparedStatement.setInt(5,idDoc);
            preparedStatement.execute();
            query="DELETE FROM pret WHERE `pret`.`idPret` = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPret);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshPretTable();
        refreshEmpruntTable();
    }

    public void retournerEmprunt() {
        int idEmprunt = empruntTableView.getSelectionModel().getSelectedItem().idProperty();
        query = "UPDATE `emprunt` SET `retourne` = '1' WHERE `emprunt`.`idEmprunt` = "+idEmprunt;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refreshEmpruntTable();
    }
}
