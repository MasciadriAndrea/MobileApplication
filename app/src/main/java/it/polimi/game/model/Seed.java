package it.polimi.game.model;

import android.graphics.Bitmap;

import it.polimi.framework.util.RandomNumberGenerator;

public class Seed {
    private float x, y;
    private Bitmap bm;
    Integer position;
    Boolean visible;

    public Seed(Integer position,Bitmap bm) {
        updatePosition(position);
        this.bm=bm;
        this.visible=true;
    }

    public Boolean inTray(){
        if((position==6)||(position==13)){
            return true;
        }
        return false;
    }

    public Boolean isVisible(){
        return visible;
    }

    public void updatePosition(Integer position) {
        this.makeVisible();
        this.position=position;
        float x,y;
        if(position<6){
            x=Game.getInstance().getxBowl()[position];
            y=Game.getInstance().getyBowl()[position];
        }else{
             if(position==6){
                 x=Game.getInstance().getxTray()[0];
                 y=Game.getInstance().getyTray()[0];
             }else{
             if(position<13){
                 x=Game.getInstance().getxBowl()[position-1];
                 y=Game.getInstance().getyBowl()[position-1];
             }else{
                 x=Game.getInstance().getxTray()[1];
                 y=Game.getInstance().getyTray()[1];
             }
             }
        }
        int dx= RandomNumberGenerator.getRandIntBetween(70, 170);
        int dy=RandomNumberGenerator.getRandIntBetween(70, 170);
        this.x = x+dx;
        this.y = y+dy;
    }

    public void makeVisible(){
        visible=true;
    }

    public void makeInvisible(){
        visible=false;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getBm() {
        return bm;
    }

    public Integer getPosition() {
        return position;
    }
}