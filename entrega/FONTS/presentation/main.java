package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Sergi Casau Pueyo
 */

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/presentation/viewLogIn.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().addAll(String.valueOf(this.getClass().getResource("test.css")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recommender");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
