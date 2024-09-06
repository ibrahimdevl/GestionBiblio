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
import models.Member;
import models.Document;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MemberHomeController implements ControllerMethods, Initializable {

    @FXML private Label welcomeLabel;
    @FXML
    private TableView<Document> documentTableView;
    @FXML
    private TableColumn<Document,String> titleCol;
    @FXML
    private TableColumn<Document,String> authorCol;
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
    protected Member member;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLoan();
    }

    private void loadLoan(){
        refreshDocumentTable();
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
    }

    public void refreshDocumentTable() {
        try {
            documentDataList.clear();
            query = "SELECT * FROM documents " +
                    "LEFT JOIN loans ON documents.document_id = loans.document_id " +
                    "WHERE loans.is_returned = 1 OR loans.loan_id IS NULL ;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int documentId = resultSet.getInt("document_id");
                String reference = resultSet.getString("reference");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Document document = new Document(documentId, reference, title, author);
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
    public void displayName(String lastName, String firstName){
        welcomeLabel.setText("Welcome "+lastName+" "+firstName+"!");
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

    public void reserve(ActionEvent actionEvent) {
        Document document = documentTableView.getSelectionModel().getSelectedItem();

        query = "INSERT INTO `reservations` (`document_id`, `member_id`, `reservation_date`, `is_validated`) VALUES (?,?,?,'0')";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, document.getDocumentId());
            preparedStatement.setInt(2, member.getUserId());
            preparedStatement.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Reservation request sent to librarian");
        alert.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> alert.hide()));
        timeline.play();
    }
}