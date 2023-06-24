package com.example.snake;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Game<T extends Obstacle> implements Initializable {

    @FXML
    private ImageView ImagePlayer1;

    @FXML
    private ImageView ImagePlayer2;

    @FXML
    private ImageView Player1;

    @FXML
    private ImageView Player2;

    @FXML
    private ImageView MainMenu;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label Player1name;

    @FXML
    private Label Player2name;

    @FXML
    private ImageView HandArrow;

    @FXML
    private ImageView one;

    @FXML
    private ImageView two;

    @FXML
    private ImageView three;

    @FXML
    private ImageView four;

    @FXML
    private ImageView five;

    @FXML
    private ImageView six;

    @FXML
    private ImageView ImagePlayer11;

    @FXML
    private ImageView ImagePlayer21;

    @FXML
    private Label WinningTextP1;

    @FXML
    private Label WinningTextP2;

    @FXML
    private Label Congo;

    @FXML
    private Pane pane;

    @FXML
    private Label Player1name1;

    @FXML
    private Label Player2name1;

    private boolean isPlayer1 = true;
    private Player<T> player1;
    private Player<T> player2;
    private final ArrayList<T>obstacles = new ArrayList<>();
    private final ArrayList<ImageView>numbers = new ArrayList<>();
    private Dice dice;
    private boolean fromwin = false;

    public void showDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("GO TO MAIN MENU");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            anchorPane.setDisable(true);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),anchorPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(actionEvent -> {
                try {
                    switchtoMainMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeTransition.play();
        }
        else {
            alert.close();
        }
    }

    public void switchtoMainMenu() throws IOException {
        if(fromwin){
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),anchorPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(actionEvent -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) (MainMenu.getScene().getWindow());
                assert root != null;
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
            fadeTransition.play();
        }
        else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Stage stage = (Stage) (MainMenu.getScene().getWindow());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void setPlayer1name(String name){
        player1.setPlayerName(name);
        Player1name.setText("ID : "+player1.getId()+" Name : "+player1.getPlayerName());
    }

    public void setPlayer2name(String name){
        player2.setPlayerName(name);
        Player2name.setText("ID : " +player2.getId()+" Name : "+player2.getPlayerName());
    }

    public void throwDice(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
        translateTransition.setCycleCount(500);
        translateTransition.setAutoReverse(true);
        translateTransition.setNode(HandArrow);
        translateTransition.setToX(10);
        translateTransition.setFromX(0);
        if(isPlayer1){
            translateTransition.stop();
            HandArrow.setRotate(0);
            HandArrow.setLayoutX(762);
            dice.rollDice(player1);
            translateTransition.play();
            player2.getTranslateTransition().play();
            player1.getTranslateTransition().stop();
        }
        else {
            translateTransition.stop();
            HandArrow.setRotate(180);
            HandArrow.setLayoutX(654);
            dice.rollDice(player2);
            translateTransition.play();
            player1.getTranslateTransition().play();
            player2.getTranslateTransition().stop();
        }
        if(player1.isWin() && player2.isWin()){
            isPlayer1 = !isPlayer1;
        }
        else{
            WinningScreen();
        }
    }

    private void WinningScreen(){
        fromwin = true;
        player1.getTranslateTransition().stop();
        player2.getTranslateTransition().stop();
        HandArrow.setVisible(false);
        TranslateTransition translateTransition2 = new TranslateTransition();
        translateTransition2.setByX(10);
        translateTransition2.setAutoReverse(true);
        translateTransition2.setCycleCount(500);
        translateTransition2.setDuration(Duration.millis(1000));
        translateTransition2.setNode(WinningTextP1);
        translateTransition2.play();
        TranslateTransition translateTransition3 = new TranslateTransition();
        translateTransition3.setByX(10);
        translateTransition3.setAutoReverse(true);
        translateTransition3.setCycleCount(500);
        translateTransition3.setDuration(Duration.millis(1000));
        translateTransition3.setNode(WinningTextP2);
        translateTransition3.play();
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
        translateTransition.setToY(12);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(500);
        if(!player1.isWin()){
            ImagePlayer21.setVisible(false);
            translateTransition.setNode(ImagePlayer11);
            WinningTextP1.setText("W I N N E R");
            WinningTextP2.setVisible(false);
            Player1name1.setText(Player1name.getText());
            Player2name1.setVisible(false);
        }
        else{
            ImagePlayer11.setVisible(false);
            translateTransition.setNode(ImagePlayer21);
            WinningTextP2.setText("W I N N E R");
            WinningTextP1.setVisible(false);
            Player2name1.setText(Player2name.getText());
            Player1name1.setVisible(false);
        }
        translateTransition.play();
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1000));
        translateTransition1.setToY(5);
        translateTransition1.setAutoReverse(true);
        translateTransition1.setCycleCount(500);
        translateTransition1.setNode(Congo);
        translateTransition1.play();
        pane.setVisible(true);
    }

    private void setCoordinates(){
        obstacles.add((T)new Ladder(true,440,495,320,315,6));
        obstacles.add((T) new Ladder(true,200,555,260,435,5));
        obstacles.add((T) new Ladder(true,440,375,500,315,9));
        obstacles.add((T) new Ladder(true,560,315,500,195,9));
        obstacles.add((T) new Ladder(true,80,315,140,195,3));
        obstacles.add((T) new Ladder(true,80,195,20,75,1));
        obstacles.add((T) new Ladder(false,380,135,500,15,8));
        obstacles.add((T) new Snake(true,20,375,140,555,3));
        obstacles.add((T) new Snake(false,140,315,140,495,2));
        obstacles.add((T) new Snake(true,380,435,260,555,5));
        obstacles.add((T) new Snake(true,80,15,20,315,1));
        obstacles.add((T) new Snake(false,260,135,140,255,2));
        obstacles.add((T) new Snake(true,320,195,260,315,5));
        obstacles.add((T) new Snake(false,380,255,560,375,9));
        obstacles.add((T) new Snake(false,500,75,440,255,7));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setVisible(false);
        numbers.add(one);
        numbers.add(two);
        numbers.add(three);
        numbers.add(four);
        numbers.add(five);
        numbers.add(six);
        dice = new Dice(numbers);
        setCoordinates();
        player1 = new Player<>(Player1,ImagePlayer1,obstacles);
        player2 = new Player<>(Player2,ImagePlayer2,obstacles);
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setByX(5);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(500);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setNode(MainMenu);
        translateTransition.play();
        player1.getTranslateTransition().play();
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(1000));
        translateTransition2.setCycleCount(500);
        translateTransition2.setAutoReverse(true);
        translateTransition2.setNode(HandArrow);
        translateTransition2.setToX(10);
        translateTransition2.setFromX(0);
        HandArrow.setRotate(180);
        HandArrow.setLayoutX(654);
        translateTransition2.play();
    }
}
