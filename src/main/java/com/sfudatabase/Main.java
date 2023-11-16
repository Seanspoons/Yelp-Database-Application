package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double ASPECT_RATIO = 16.0 / 9.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/MainScene.fxml"));

        Screen userScreen = Screen.getPrimary();
        javafx.geometry.Rectangle2D screenBounds = userScreen.getVisualBounds();

        double viewportHeight = screenBounds.getHeight() * 0.8;
        double viewportWidth = viewportHeight * ASPECT_RATIO;

        Scene mainScene = new Scene(root, viewportWidth, viewportHeight);
        mainScene.getStylesheets().add(getClass().getResource("../resources/css/mainScene.css").toExternalForm());

        primaryStage.setTitle("Yelp Database Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
