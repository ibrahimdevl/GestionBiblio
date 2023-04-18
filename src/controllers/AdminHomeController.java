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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Adherent;
import models.Bibliothecaire;
import models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminHomeController implements ControllerMethods, Initializable {

    protected Utilisateur utilisateur;
    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Adherent adherent = null;
    Bibliothecaire bibliothecaire = null;


    // Execute query and retrieve data
    ObservableList<Adherent> AdherentList = FXCollections.observableArrayList();
    ObservableList<Bibliothecaire> BibliothecaireList = FXCollections.observableArrayList();
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField adherentSearchField;
    @FXML
    private TextField biblioSearchField;
    @FXML
    private TableView<Adherent> adherentTableView;
    @FXML
    private TableColumn<Adherent, String> nomCol;
    @FXML
    private TableColumn<Adherent, String> prenomCol;
    @FXML
    private TableColumn<Adherent, Integer> cinCol;
    @FXML
    private TableView<Bibliothecaire> biblioTableView;
    @FXML
    private TableColumn<Bibliothecaire, String> biblioNomCol;
    @FXML
    private TableColumn<Bibliothecaire, String> biblioPrenomCol;
    @FXML
    private TableColumn<Bibliothecaire, Date> biblioDateCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }


    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/addAdherent.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void refreshAdherentTable() {
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
            Logger.getLogger(AdherentHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshBibliothecaireTable() {
        try {
            BibliothecaireList.clear();

            query = "SELECT utilisateur.* , bibliothecaire.dateEmbauche , bibliothecaire.salaire FROM `utilisateur`, `bibliothecaire` WHERE bibliothecaire.idUtlstr=utilisateur.idUtlstr";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Bibliothecaire biblio = new Bibliothecaire();
                biblio.createBibliothecaireInstance(resultSet);
                BibliothecaireList.add(biblio);
                biblioTableView.setItems(BibliothecaireList);

            }


        } catch (SQLException ex) {
            Logger.getLogger(AdherentHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void print(MouseEvent event) {
    }

    private void loadDate() {

        refreshAdherentTable();

        nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        cinCol.setCellValueFactory(new PropertyValueFactory<>("Cin"));
        adherentTableView.setItems(AdherentList);

        refreshBibliothecaireTable();

        biblioNomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        biblioPrenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        biblioDateCol.setCellValueFactory(new PropertyValueFactory<>("DateEmbauche"));
        biblioTableView.setItems(BibliothecaireList);


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

    public void adherentEditSelectedRow(ActionEvent e) {
        adherent = adherentTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addAdherent.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AddAdherentController addStudentController = loader.getController();
        addStudentController.setUpdate(true);
        addStudentController.setTextField(adherent.getIdUtlstr(), adherent.getNomUtlstr(),adherent.getMotDePasse(), adherent.getNom(), adherent.getPrenom(),adherent.getAddress(),adherent.getNumTel(),adherent.getCin(),adherent.getDateInscription());
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

    public void biblioSearchForRows() {
        String searchString = biblioSearchField.getText();
        if (searchString == null || searchString.isEmpty()) {
            biblioTableView.setItems(BibliothecaireList);
        } else {
            ObservableList<Bibliothecaire> filteredData = FXCollections.observableArrayList();
            for (Bibliothecaire bibliothecaire : BibliothecaireList) {
                if (bibliothecaire.matchesSearch(searchString)) {
                    filteredData.add(bibliothecaire);
                }
            }
            biblioTableView.setItems(filteredData);
        }
    }

    public void biblioEditSelectedRow(ActionEvent e) {
    }

    public void biblioDeleteSelectedRow() {
        try {
            bibliothecaire = biblioTableView.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `utilisateur` WHERE idUtlstr  =" + bibliothecaire.getIdUtlstr();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshBibliothecaireTable();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //go back to login page
    public void redirectLogin(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);

        centerScene(stage, scene);

        stage.setScene(scene);
        stage.show();
    }

    //custom welcome message
    public void displayName(String nom, String prenom) {
        welcomeLabel.setText("Bienvenue " + nom + " " + prenom + "!");
    }


    public void adherentAdd(ActionEvent actionEvent) {
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

    public void biblioAdd(ActionEvent actionEvent) {
    }
}
