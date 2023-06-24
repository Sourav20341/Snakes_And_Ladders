package com.example.snake;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Dice{
    private final ArrayList<ImageView>numbers;
    private ImageView ref;

    public Dice(ArrayList<ImageView>numbers) {
        this.numbers = numbers;
    }

    public void rollDice(Player player){
        int rand = ThreadLocalRandom.current().nextInt(1,7);
        player.setPrevPos(player.getPos());
        for(ImageView num : numbers){
            if(rand == numbers.indexOf(num)+1){
                num.setVisible(true);
                num.setDisable(false);
                ref = num;
            }
            else{
                num.setVisible(false);
                num.setDisable(true);
            }
        }
        if(rand == 1 && player.isLockstate()){
            player.setLockstate(false);
        }
        for(int i  = 0;i<rand;i++) {
            if (!player.isLockstate()) {
                player.UpdatePos();
            }
        }
        if(!player.isLockstate()){
            if (player.getLayoutY() < 15) {
                ref.setDisable(false);
                player.setLayoutY(player.getPrevLayoutY());
                player.setLayoutX(player.getPrevLayoutX());
                player.setPos(player.getPrevPos());
                player.setForwarding(false);
            } else {
                player.MovementTransition(player.getLayoutX(), player.getLayoutY(), ref);
                if (player.getLayoutX() == 20 && player.getLayoutY() == 15) {
                    player.setWin(true);
                }
            }
        }
    }
}
