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

public class LibrarianHomeController implements ControllerMethods, Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Member member = null;
    Librarian librarian;

    ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField memberSearchField;
    @FXML
    private TableView<Member> memberTableView;
    @FXML
    private TableColumn<Member, String> lastNameCol;
    @FXML
    private TableColumn<Member, String> firstNameCol;
    @FXML
    private TableColumn<Member, Integer> idNumberCol;
    @FXML
    private TableView<LoanReservationData> reservationTableView;
    @FXML
    private TableColumn<LoanReservationData, String> reservationDocumentCol;
    @FXML
    private TableColumn<LoanReservationData, String> reservationMemberCol;
    @FXML
    private TableColumn<LoanReservationData, Date> reservationDateCol;

    @FXML
    private TableView<LoanReservationData> loanTableView;
    @FXML
    private TableColumn<Document, String> loanDocumentCol;
    @FXML
    private TableColumn<Document, String> loanMemberCol;
    @FXML
    private TableColumn<Document, Date> loanDateCol;

    ObservableList<LoanReservationData> reservationDataList = FXCollections.observableArrayList();
    ObservableList<LoanReservationData> loanDataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMember();
        loadReservation();
        loadLoan();
    }
    private void loadMember() {
        refreshMemberTable();
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        idNumberCol.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
    }

    private void loadReservation(){
        refreshReservationTable();
        reservationDocumentCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        reservationMemberCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        reservationDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadLoan(){
        refreshLoanTable();
        loanDocumentCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        loanMemberCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        loanDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void refreshMemberTable() {
        try {
            memberList.clear();

            query = "SELECT users.* , members.id_number , members.registration_date FROM `users`, `members` WHERE members.user_id=users.user_id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member();
                member.createMemberInstance(resultSet);
                memberList.add(member);
                memberTableView.setItems(memberList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void refreshReservationTable() {
        try {
            reservationDataList.clear();
            query = "SELECT reservations.reservation_id as id, reservations.document_id, reservations.member_id, documents.title, CONCAT(users.last_name, ' ', users.first_name) as name, reservations.reservation_date as date\n" +
                    "FROM reservations\n" +
                    "JOIN documents ON reservations.document_id = documents.document_id\n" +
                    "JOIN users ON reservations.member_id = users.user_id\n" +
                    "WHERE reservations.is_validated=0";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("id");
                int documentId = resultSet.getInt("document_id");
                int memberId = resultSet.getInt("member_id");
                String title = resultSet.getString("title");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LoanReservationData loanReservationData = new LoanReservationData(reservationId, documentId, memberId, title, name, date);
                reservationDataList.add(loanReservationData);
                reservationTableView.setItems(reservationDataList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void refreshLoanTable() {
        try {
            loanDataList.clear();
            query = "SELECT loans.loan_id as id, loans.document_id, loans.member_id, documents.title, CONCAT(users.last_name, ' ', users.first_name) as name, loans.loan_date as date\n" +
                    "FROM loans\n" +
                    "JOIN documents ON loans.document_id = documents.document_id\n" +
                    "JOIN users ON loans.member_id = users.user_id\n" +
                    "WHERE loans.is_returned=0";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int loanId = resultSet.getInt("id");
                int documentId = resultSet.getInt("document_id");
                int memberId = resultSet.getInt("member_id");
                String title = resultSet.getString("title");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LoanReservationData loanReservationData = new LoanReservationData(loanId, documentId, memberId, title, name, date);
                loanDataList.add(loanReservationData);
                loanTableView.setItems(loanDataList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void memberSearchForRows() {
        String searchString = memberSearchField.getText();
        if (searchString == null || searchString.isEmpty()) {
            memberTableView.setItems(memberList);
        } else {
            ObservableList<Member> filteredData = FXCollections.observableArrayList();
            for (Member member : memberList) {
                if (member.matchesSearch(searchString)) {
                    filteredData.add(member);
                }
            }
            memberTableView.setItems(filteredData);
        }
    }

    public void memberEditSelectedRow() {
        member = memberTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addMember.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        redirectMemberEdit(loader, member);
    }

    public static void redirectMemberEdit(FXMLLoader loader, Member member) {
        AddMemberController addMemberController = loader.getController();
        addMemberController.setUpdate();
        addMemberController.setTextField(member.getUserId(), member.getUsername(), member.getPassword(),
                member.getLastName(), member.getFirstName(), member.getAddress(), member.getPhone(), member.getCin(),
                member.getDateInscription());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public void memberDeleteSelectedRow() {
        try {
            member = memberTableView.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `users` WHERE user_id = " + member.getUserId();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshMemberTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void memberAdd() {
        member = memberTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addMember.fxml"));
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
    public void displayName(String lastName, String firstName) {
        welcomeLabel.setText("Welcome " + lastName + " " + firstName + "!");
    }

    public void validateReservation() {
        LoanReservationData item = reservationTableView.getSelectionModel().getSelectedItem();
        int memberId = item.memberIdProperty();
        int documentId = item.documentIdProperty();
        int reservationId = item.idProperty();

        try {
            query="INSERT INTO `loans` (`loan_date`, `return_date`, `member_id`, `librarian_id`, `document_id`, `is_returned`) VALUES (?, ?, ?, ?, ?, '0')";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, 2);
            java.sql.Date twoWeeksFromNow = new java.sql.Date(calendar.getTimeInMillis());
            preparedStatement.setDate(2, twoWeeksFromNow);
            preparedStatement.setInt(3, memberId);
            preparedStatement.setInt(4, librarian.getUserId());
            preparedStatement.setInt(5, documentId);
            preparedStatement.execute();
            query="DELETE FROM reservations WHERE `reservations`.`reservation_id` = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservationId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshReservationTable();
        refreshLoanTable();
    }

    public void returnLoan() {
        int loanId = loanTableView.getSelectionModel().getSelectedItem().idProperty();
        query = "UPDATE `loans` SET `is_returned` = '1' WHERE `loans`.`loan_id` = "+loanId;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refreshLoanTable();
    }
}