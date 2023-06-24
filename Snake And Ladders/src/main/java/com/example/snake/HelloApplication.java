package com.example.snake;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartingLoad.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(actionEvent -> {
            stage.close();
            try {
                FXMLLoader newfxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene newscene = new Scene(newfxmlLoader.load(), 900, 600);
                stage.setTitle("S N A K E  A N D  L A D D E R");
                stage.setScene(newscene);
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    public static void main(String[] args) {
        launch();
    }
}