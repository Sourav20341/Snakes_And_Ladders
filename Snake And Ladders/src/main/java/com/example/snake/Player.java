package com.example.snake;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.util.ArrayList;

public class Player<T extends Obstacle>{
    private static int ID = 1;
    private final int id;
    private String PlayerName;
    private int pos = 1;
    private int prevPos = 0;
    private boolean startPlayer = true;
    private boolean forwarding = true;
    private final ImageView imageView;
    private final TranslateTransition translateTransition = new TranslateTransition();
    private double LayoutX = 0;
    private double LayoutY = 0;
    private double prevLayoutX;
    private double prevLayoutY;
    private boolean isWin = false;
    private boolean lockstate = true;
    private final ArrayList<T>obstacles;
    private boolean find = false;

    public Player(ImageView imageView, ImageView tokkenImage,ArrayList<T>obstacles) {
        this.imageView = imageView;
        id = ID;
        translateTransition.setByY(10);
        translateTransition.setCycleCount(500);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setAutoReverse(true);
        translateTransition.setNode(tokkenImage);
        translateTransition.setFromY(0);
        prevLayoutX = imageView.getLayoutX();
        prevLayoutY = imageView.getLayoutY();
        ID++;
        this.obstacles = obstacles;
    }

    public boolean isWin() {
        return !isWin;
    }

    public boolean isLockstate() {
        return lockstate;
    }

    public void setLockstate(boolean lockstate) {
        this.lockstate = lockstate;
    }

    public int getId() {
        return id;
    }

    public void setLayoutX(double layoutX) {
        LayoutX = layoutX;
    }

    public void setForwarding(boolean forwarding) {
        this.forwarding = forwarding;
    }

    public void setLayoutY(double layoutY) {
        LayoutY = layoutY;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPrevPos() {
        return prevPos;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public double getLayoutX() {
        return LayoutX;
    }

    public double getLayoutY() {
        return LayoutY;
    }

    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

    public void UpdatePos(){
        if(startPlayer){
            LayoutX = 20;
            LayoutY = 555;
            startPlayer = false;
        }
        else if(forwarding){
            if(pos == 0){
                LayoutY -= 60;
            }
            else{
                LayoutX+=60;
            }
            pos++;
            if(pos == 10){
                forwarding = false;
            }
        }
        else{
            if(pos == 10){
                LayoutY -= 60;
            }
            else{
                LayoutX -= 60;
            }
            pos--;
            if(pos== 0){
                forwarding = true;
            }
        }
    }

    public void setPrevPos(int prevPos) {
        this.prevPos = prevPos;
    }

    public double getPrevLayoutX() {
        return prevLayoutX;
    }

    public double getPrevLayoutY() {
        return prevLayoutY;
    }

    private void ObstacleCheck(){
        for(T obs : obstacles){
            if(LayoutX == obs.getInitposX() && LayoutY == obs.getInitposY()){
                forwarding = obs.isForward();
                pos = obs.getPos();
                LayoutX = obs.getFinalposX();
                LayoutY = obs.getFinalposY();
                find = true;
                break;
            }
        }
    }

    public int getPos() {
        return pos;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public void MovementTransition(double currentLayoutX, double currentLayoutY,ImageView Dice){
        imageView.setLayoutY(prevLayoutY);
        imageView.setLayoutX(prevLayoutX);
        Dice.setDisable(true);
        PathTransition translateTransition = new PathTransition();
        translateTransition.setNode(imageView);
        Path path = new Path();
        double fromX = imageView.getLayoutBounds().getWidth() / 2;
        double fromY = imageView.getLayoutBounds().getHeight() / 2;
        currentLayoutX -= imageView.getLayoutX() - imageView.getLayoutBounds().getWidth() / 2;
        currentLayoutY -= imageView.getLayoutY() - imageView.getLayoutBounds().getHeight() / 2;
        double finalCurrentLayoutX = currentLayoutX;
        double finalCurrentLayoutY = currentLayoutY;
        path.getElements().add(new MoveTo(fromX,fromY));
        path.getElements().add(new LineTo(finalCurrentLayoutX,finalCurrentLayoutY));
        translateTransition.setPath(path);
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.play();
        translateTransition.setOnFinished(actionEvent -> {
            if(!isWin){
                Dice.setDisable(false);
            }
            prevLayoutY = LayoutY;
            prevLayoutX = LayoutX;
            ObstacleCheck();
            if(find){
                Dice.setDisable(true);
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1),imageView);
                translateTransition1.setByY(LayoutY-prevLayoutY);
                translateTransition1.setByX(LayoutX-prevLayoutX);
                translateTransition1.play();
                translateTransition1.setOnFinished(actionEvent1 -> {Dice.setDisable(false);prevLayoutY = LayoutY;
                prevLayoutX = LayoutX;});
                find = false;
            }
        });
    }
}
