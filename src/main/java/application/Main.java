package application;

import controllers.ControllerMethods;
import databaseConnection.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application implements ControllerMethods {
    @Override
    public void start(Stage stage) throws Exception {
        // Run the script to create the database and tables
        DatabaseConnection.runScript("src/main/resources/libraryproject.sql");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.setTitle("Bibliothéque");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        centerScene(stage,scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}