package it.polimi.game.model;

public class Seed {
    private float x, y;

    public Seed(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float x,float y) {
        this.x=x;
        this.y=y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}