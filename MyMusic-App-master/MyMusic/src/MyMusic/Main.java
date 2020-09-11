package MyMusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up layout
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));

        // Set the title
        primaryStage.setTitle("My Music");

        // Initialize the scene
        scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX(), Screen.getPrimary().getBounds().getMaxY() - 100);

        // Set the initial scene
        primaryStage.setScene(scene);

        // Add stylesheet
        scene.getStylesheets().add(Main.class.getResource("css/styles.css").toExternalForm());

        // Initialize the controllers

        // Show the scene
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
