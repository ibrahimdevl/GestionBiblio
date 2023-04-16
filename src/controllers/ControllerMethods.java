package controllers;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public interface ControllerMethods {
    //Method to center the window in the middle of the screen
    public default void centerScene(Stage stage,Scene scene) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double x = (screenBounds.getWidth() - sceneWidth) / 2;
        double y = (screenBounds.getHeight() - sceneHeight) / 2;
        stage.setX(x);
        stage.setY(y);
    }
}
