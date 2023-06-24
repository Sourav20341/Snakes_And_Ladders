package com.example.snake;

public abstract class Obstacle{

    private final boolean isForward;
    private final double initposX;
    private final double initposY;
    private final double finalposX;
    private final double finalposY;
    private final int pos;

    public Obstacle(boolean isForward, double initposX, double initposY, double finalposX, double finalposY, int pos) {
        this.isForward = isForward;
        this.initposX = initposX;
        this.initposY = initposY;
        this.finalposX = finalposX;
        this.finalposY = finalposY;
        this.pos = pos;
    }

    public boolean isForward() {
        return isForward;
    }

    public double getInitposX() {
        return initposX;
    }

    public double getInitposY() {
        return initposY;
    }

    public double getFinalposX() {
        return finalposX;
    }

    public double getFinalposY() {
        return finalposY;
    }

    public int getPos() {
        return pos;
    }
}

