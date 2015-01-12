package it.polimi.game.model;

import android.util.Log;

public class Bee {
    private float x, y;
    private float xHome, yHome;
    private double ang,angHome;

    public Bee(float x, float y,double ang) {
        this.x = x;
        this.y = y;
        this.xHome=x;
        this.yHome=y;
        this.angHome=ang;
        this.ang=ang;
    }

    public void update(float x,float y) {
        this.x=x;
        this.y=y;
    }

    public Boolean atHome(){
        if((x==xHome)&&(y==yHome)){
            return true;
        }   return false;
    }

    public void fly(float x, float y){
        float stepX;
        float stepY;
        int vel=40;
        int angVel=10;
        //boolean finishRot=false;
        float dx=x-getX();
        float dy=y-getY();
        double definedAngle=(Math.toDegrees(Math.atan2(dx, -dy)))%360;
        /*if((Math.abs(definedAngle-this.ang)<30)||((Math.abs(definedAngle+this.ang)<30))){
            finishRot=true;
        }*/
        //TODO fix rotation
        this.ang=definedAngle;
        /*if(definedAngle > this.ang){
            if(definedAngle>this.ang+angVel){
                this.ang+=angVel;
            }else{
                this.ang=definedAngle;
            }
        }else{
            if(definedAngle<this.ang-angVel){
                this.ang-=angVel;
            }else{
                this.ang=definedAngle;
            }
        }*/

    // if(((Math.abs(definedAngle - this.ang))<30)&&(!atHome())){
      //  if(finishRot) {
            if (getX() > x) {
                if (getX() - x < vel) {
                    stepX = x - getX();
                } else {
                    stepX = -vel;
                }
            } else {
                if (x - getX() < vel) {
                    stepX = x - getX();
                } else {
                    stepX = +vel;
                }
            }
            if (getY() > y) {
                if (getY() - y < vel) {
                    stepY = y - getY();
                } else {
                    stepY = -vel;
                }
            } else {
                if (y - getY() < vel) {
                    stepY = y - getY();
                } else {
                    stepY = +vel;
                }
            }
            update(getX() + stepX, getY() + stepY);
            /*if (atHome()) {
                ang = this.angHome;
            }*/
       // }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getxHome() {
        return xHome;
    }

    public float getyHome() {
        return yHome;
    }

    public double getAng() {
        return ang;
    }

}
