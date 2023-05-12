package controllers;

import application.Main;
import databaseConnection.DatabaseConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Adherent;
import models.Document;
import models.EmpruntPretData;
import models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AdherentHomeController implements ControllerMethods, Initializable {

    @FXML private Label welcomeLabel;
    @FXML
    private TableView<Document> documentTableView;
    @FXML
    private TableColumn<Document,String> titreCol;
    @FXML
    private TableColumn<Document,String> auteurCol;
    @FXML
    private TextField documentSearchField;
    ObservableList<Document> documentDataList = FXCollections.observableArrayList();
    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String query;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    private Stage stage;
    private Scene scene;
    protected Adherent adherent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmprunt();
    }

    private void loadEmprunt(){
        refreshDocumentTable();
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
    }

    public void refreshDocumentTable() {
        try {
            documentDataList.clear();
            query = "SELECT * FROM document " +
                    "LEFT JOIN emprunt ON document.idDoc = emprunt.idDoc " +
                    "WHERE emprunt.retourne = 1 OR emprunt.idEmprunt IS NULL ;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idDoc = resultSet.getInt("idDoc");
                String reference = resultSet.getString("reference");
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                Document document = new Document(idDoc,reference,titre,auteur);
                documentDataList.add(document);
                documentTableView.setItems(documentDataList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

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


    public void documentSearchForRows() {
        String searchString = documentSearchField.getText();
        if (searchString == null || searchString.isEmpty()) {
            documentTableView.setItems(documentDataList);
        } else {
            ObservableList<Document> filteredData = FXCollections.observableArrayList();
            for (Document document : documentDataList) {
                if (document.matchesSearch(searchString)) {
                    filteredData.add(document);
                }
            }
            documentTableView.setItems(filteredData);
        }
    }

    public void emprunter(ActionEvent actionEvent) {
        Document document=documentTableView.getSelectionModel().getSelectedItem();

        query="INSERT INTO `pret` (`idDoc`, `idAdh`, `datePret`, `validee`) VALUES (?,?,?,'0')";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,document.getIdDoc());
            preparedStatement.setInt(2,adherent.getIdUtlstr());
            preparedStatement.setDate(3,new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Pret envoyée au bibliothecaire pour emprunter");
        alert.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> alert.hide()));
        timeline.play();

    }
}
