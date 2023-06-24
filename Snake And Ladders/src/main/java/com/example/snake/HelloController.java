package com.example.snake;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ImageView Snake;

    @FXML
    private ImageView Snake1;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView Play;

    @FXML
    private ImageView Exit;

    @FXML
    private ImageView Loading;

    private RotateTransition rotateTransition;
    private String player1name;
    private String player2name;

    public void showDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("EXIT GAME");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Exit();
        } else {
            alert.close();
        }
    }

    public void textinput(){
        TextInputDialog textInputDialog = new TextInputDialog("Enter name");
        textInputDialog.setHeaderText("Enter Player 1 name");
        textInputDialog.setContentText("Name : ");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(name -> {
            player1name = textInputDialog.getResult();
            textinput1();
        });
    }

    public void textinput1(){
        TextInputDialog textInputDialog = new TextInputDialog("Enter name");
        textInputDialog.setHeaderText("Enter Player 2 name");
        textInputDialog.setContentText("Name : ");
        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(name -> {
            try {
                player2name = textInputDialog.getResult();
                switchtoscene1();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void switchtoscene1() throws IOException {
        Scene scene = (Play).getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = loader.load();
        Game game = loader.getController();
        game.setPlayer1name(player1name);
        game.setPlayer2name(player2name);
        root.translateXProperty().set(scene.getWidth());
        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0,Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(actionEvent -> stackPane.getChildren().remove(anchorPane));
        timeline.play();
    }

    public void OnMouseHover(){
        rotateTransition = new RotateTransition();
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(10);
        rotateTransition.setCycleCount(500);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setAutoReverse(true);
        rotateTransition.setNode(Play);
        rotateTransition.play();
    }

    public void OnMouseExit(){
        Play.setRotate(0);
        rotateTransition.stop();
    }

    public void OnMouseExitHover(){
        rotateTransition = new RotateTransition();
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(-10);
        rotateTransition.setCycleCount(500);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setAutoReverse(true);
        rotateTransition.setNode(Exit);
        rotateTransition.play();
    }

    public void OnMouseExitExit(){
        Exit.setRotate(0);
        rotateTransition.stop();
    }

    public void Exit(){

        Stage stage = (Stage)Exit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition Translate = new TranslateTransition();
        Translate.setByY(400);
        Translate.setCycleCount(1500);
        Translate.setDuration(Duration.millis(3000));
        Translate.setAutoReverse(true);
        Translate.setNode(Snake);
        Translate.play();
        TranslateTransition newTranslate = new TranslateTransition();
        newTranslate.setByY(-400);
        newTranslate.setCycleCount(1500);
        newTranslate.setDuration(Duration.millis(3000));
        newTranslate.setAutoReverse(true);
        newTranslate.setNode(Snake1);
        newTranslate.play();
        FadeTransition nfadeTransition = new FadeTransition(Duration.seconds(3.5),Loading);
        nfadeTransition.setFromValue(0);
        nfadeTransition.setToValue(1);
        nfadeTransition.play();
    }

}