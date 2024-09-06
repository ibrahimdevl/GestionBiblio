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
import models.Librarian;
import models.Member;
import models.Admin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminHomeController implements ControllerMethods, Initializable {

    DatabaseConnection connect = new DatabaseConnection();
    Connection connection = connect.getConnection();
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Admin admin;
    Member member;
    Librarian librarian;

    // Execute query and retrieve data
    ObservableList<Member> memberList = FXCollections.observableArrayList();
    ObservableList<Librarian> librarianList = FXCollections.observableArrayList();
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField memberSearchField;
    @FXML
    private TextField librarianSearchField;
    @FXML
    private TableView<Member> memberTableView;
    @FXML
    private TableColumn<Member, String> lastNameCol;
    @FXML
    private TableColumn<Member, String> firstNameCol;
    @FXML
    private TableColumn<Member, Integer> idNumberCol;
    @FXML
    private TableView<Librarian> librarianTableView;
    @FXML
    private TableColumn<Librarian, String> librarianLastNameCol;
    @FXML
    private TableColumn<Librarian, String> librarianFirstNameCol;
    @FXML
    private TableColumn<Librarian, Date> librarianHireDateCol;

    @FXML
    private Pane movablePane;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        // Add mouse event handlers to make the Pane movable
        movablePane.setOnMousePressed(this::onMousePressed);
        movablePane.setOnMouseDragged(this::onMouseDragged);
    }

    private void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - xOffset;
        double deltaY = event.getSceneY() - yOffset;

        movablePane.setLayoutX(movablePane.getLayoutX() + deltaX);
        movablePane.setLayoutY(movablePane.getLayoutY() + deltaY);

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
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
    private void refreshLibrarianTable() {
        try {
            librarianList.clear();

            query = "SELECT users.* , librarians.hire_date , librarians.salary FROM `users`, `librarians` WHERE librarians.user_id=users.user_id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Librarian librarian = new Librarian();
                librarian.createLibrarianInstance(resultSet);
                librarianList.add(librarian);
                librarianTableView.setItems(librarianList);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MemberHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDate() {
        refreshMemberTable();

        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        idNumberCol.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        memberTableView.setItems(memberList);

        refreshLibrarianTable();

        librarianLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        librarianFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        librarianHireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        librarianTableView.setItems(librarianList);
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
        LibrarianHomeController.redirectMemberEdit(loader, member);
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

    public void librarianSearchForRows() {
        String searchString = librarianSearchField.getText();
        if (searchString == null || searchString.isEmpty()) {
            librarianTableView.setItems(librarianList);
        } else {
            ObservableList<Librarian> filteredData = FXCollections.observableArrayList();
            for (Librarian librarian : librarianList) {
                if (librarian.matchesSearch(searchString)) {
                    filteredData.add(librarian);
                }
            }
            librarianTableView.setItems(filteredData);
        }
    }

    public void librarianEditSelectedRow() {
        librarian = librarianTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addLibrarian.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AddLibrarianController addLibrarianController = loader.getController();
        addLibrarianController.setUpdate();
        addLibrarianController.setTextField(librarian.getUserId(), librarian.getUsername(), librarian.getPassword(), librarian.getLastName(), librarian.getFirstName(), librarian.getAddress(), librarian.getPhone(), librarian.getSalary(), librarian.getHireDate());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public void librarianDeleteSelectedRow() {
        try {
            librarian = librarianTableView.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `users` WHERE user_id = " + librarian.getUserId();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshLibrarianTable();
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
    public void displayName(String lastName, String firstName) {
        welcomeLabel.setText("Welcome " + lastName + " " + firstName + "!");
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

    public void librarianAdd() {
        librarian = librarianTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scenes/addLibrarian.fxml"));
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
}