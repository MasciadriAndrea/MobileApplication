package it.polimi.game.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import it.polimi.core.GameMainActivity;

public class Game {
    private static Game instance = null;
    private GameHandler gh;
    private Activity gameActivity;
    private Activity loadActivity;
    private Activity menuActivity;
    private AssetManager assets;
    private Bee bee1,bee2;
    private List<Seed> seeds;
    private Integer xBee1,yBee1,xBee2,yBee2;
    Integer[] xBowl;Integer[] xTray;Integer[] xLabel;Integer[] xNames;
    Integer[] yBowl;Integer[] yTray;Integer[] yLabel;Integer[] yNames;
    private Boolean playable;
    private Boolean graphic;
    private Boolean sound;
    private Boolean music;
    private Boolean tableMode;
    private Integer nSeeds;
    private Integer sizeBowl;
    private int angp1,angp2;
    private Canvas canvas;
    private Boolean switchPlayer;
    private int angleScreen;

    protected Game() {
        seeds=new ArrayList<Seed>();
        playable=true;
        assets = null;
        this.gh=null;
        gameActivity=null;
        menuActivity= null;
        this.initializateCoordinates();
    }

    public void initializateCoordinates(){
        switchPlayer=false;
        angleScreen=0;
        xBee1=1426;yBee1=475;
        xBee2=244;yBee2=475;
        xNames= new Integer[]{1085,585};
        yNames= new Integer[]{600,600};
        bee1=new Bee(xBee1,yBee1,180);
        bee2=new Bee(xBee2,yBee2,0);
        sizeBowl=300;
        xBowl=new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
        yBowl=new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
        xLabel=new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
        yLabel=new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
        xTray=new Integer[]{1696,40};
        yTray=new Integer[]{500,500};
        for(int i=0;i<6;i++){
            xBowl[i]=60+(sizeBowl*i);
            xBowl[i+6]=1560-(sizeBowl*i);
            xLabel[i]=xBowl[i];
            xLabel[i+6]=xBowl[i+6];
            yBowl[i]=840;
            yBowl[i+6]=60;
            yLabel[i]=yBowl[i]-30;
            yLabel[i+6]=yBowl[i+6]+sizeBowl+50;
        }
    }

    public void switchPositions(){
        //switch positions of bowls and labels of p1 and p2
        //transpose vector of bowls and labels of p1 and p2
        Integer[] xBowlNew=new Integer[]{xBowl[6],xBowl[7],xBowl[8],xBowl[9],xBowl[10],xBowl[11],xBowl[0],xBowl[1],xBowl[2],xBowl[3],xBowl[4],xBowl[5]};
        Integer[] yBowlNew=new Integer[]{yBowl[6],yBowl[7],yBowl[8],yBowl[9],yBowl[10],yBowl[11],yBowl[0],yBowl[1],yBowl[2],yBowl[3],yBowl[4],yBowl[5]};
        Integer[] xLabelNew=new Integer[]{xLabel[6],xLabel[7],xLabel[8],xLabel[9],xLabel[10],xLabel[11],xLabel[0],xLabel[1],xLabel[2],xLabel[3],xLabel[4],xLabel[5]};
        Integer[] yLabelNew=new Integer[]{yLabel[6],yLabel[7],yLabel[8],yLabel[9],yLabel[10],yLabel[11],yLabel[0],yLabel[1],yLabel[2],yLabel[3],yLabel[4],yLabel[5]};
        xBowl=xBowlNew;
        yBowl=yBowlNew;
        xLabel=xLabelNew;
        yLabel=yLabelNew;
        //change positions of trays and labels
        Integer[] xTrayNew=new Integer[]{xTray[1],xTray[0]};
        Integer[] yTrayNew=new Integer[]{yTray[1],yTray[0]};
        xTray=xTrayNew;
        yTray=yTrayNew;
        //change position of bees
        float xb1=bee1.getxHome();
        float yb1=bee1.getyHome();
        double angb1=bee1.getAngHome();
        bee1.setBee(bee2.getxHome(),bee2.getyHome(),bee2.getAngHome());
        bee2.setBee(xb1,yb1,angb1);
        //change positions of names
        Integer[] xNameNew=new Integer[]{xNames[1],xNames[0]};
        Integer[] yNameNew=new Integer[]{yNames[1],yNames[0]};
        xNames=xNameNew;
        yNames=yNameNew;
    }

    public void changeAngleScreen(){
        if(this.angleScreen==0){
            this.angleScreen=180;
        } else{
            this.angleScreen=0;
        }
    }

    public List<Seed> getSeedsByPosition(Integer position,GameHandler gh){
        List<Seed> nlist=new ArrayList<Seed>();
        if(gh.equals(this.getGh())) {
            for (Seed s : seeds) {
                if (s.getPosition().equals(position)) {
                    nlist.add(s);
                    s.makeInvisible();
                }
            }
        }
            return nlist;

    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public Boolean beesInPosition(){
        if((bee1.getX()==getxBee1())&&
           (bee1.getY()==getyBee1())&&
           (bee2.getX()==getxBee2())&&
           (bee2.getY()==getyBee2())){
            return true;
        } return false;
    }
    public void makePlayable(){
        Log.v("Game","Playable");
        this.playable=true;
        GameMainActivity gma=(GameMainActivity) Game.getInstance().getGameActivity();
        gma.getsGame().setClickable(true);
    }

    public void makeUnPlayable(){
        Log.v("Game","makeUnPlayable");
        this.playable=false;
        GameMainActivity gma=(GameMainActivity) Game.getInstance().getGameActivity();
        gma.getsGame().setClickable(false);
    }

    public Boolean isPlayable(){
        return playable;
    }


    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public GameHandler getGh() {

        return gh;
    }

    public void setGh(GameHandler gh) {
        this.gh = gh;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }

    public Activity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(Activity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public Bee getBee2() {
        return bee2;
    }

    public void setBee2(Bee bee2) {
        this.bee2 = bee2;
    }

    public Bee getBee1() {
        return bee1;
    }

    public void setBee1(Bee bee1) {
        this.bee1 = bee1;
    }

    public Integer[] getxBowl() {
        return xBowl;
    }

    public void setxBowl(Integer[] xBowl) {
        this.xBowl = xBowl;
    }

    public Integer[] getyBowl() {
        return yBowl;
    }

    public void setyBowl(Integer[] yBowl) {
        this.yBowl = yBowl;
    }

    public Integer getxBee1() {
        return xBee1;
    }

    public void setxBee1(Integer xBee1) {
        this.xBee1 = xBee1;
    }

    public Integer getyBee1() {
        return yBee1;
    }

    public void setyBee1(Integer yBee1) {
        this.yBee1 = yBee1;
    }

    public Integer getxBee2() {
        return xBee2;
    }

    public void setxBee2(Integer xBee2) {
        this.xBee2 = xBee2;
    }

    public Integer getyBee2() {
        return yBee2;
    }

    public void setyBee2(Integer yBee2) {
        this.yBee2 = yBee2;
    }

    public Boolean getGraphic() {
        return graphic;
    }

    public Integer[] getxTray() {
        return xTray;
    }

    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
    }

    public Integer[] getyTray() {
        return yTray;
    }

    public void setGraphic(Boolean graphic) {
        this.graphic = graphic;
    }

    public Boolean getSound() {
        return sound;
    }

    public void setSound(Boolean sound) {
        this.sound = sound;
    }

    public Boolean getMusic() {
        return music;
    }

    public void setMusic(Boolean music) {
        this.music = music;
    }

    public Integer getnSeeds() {
        return nSeeds;
    }

    public void setnSeeds(Integer nSeeds) {
        this.nSeeds = nSeeds;
    }

    public Activity getMenuActivity() {
        return menuActivity;
    }

    public void setMenuActivity(Activity menuActivity) {
        this.menuActivity = menuActivity;
    }

    public Activity getLoadActivity() {
        return loadActivity;
    }

    public void setLoadActivity(Activity loadActivity) {
        this.loadActivity = loadActivity;
    }

    public Integer getSizeBowl() {
        return sizeBowl;
    }

    public void saveSettings(Boolean music, Boolean sound, Boolean animations,Boolean table, Integer nseeds){
        this.setGraphic(animations);
        this.setMusic(music);
        this.setnSeeds(nseeds);
        this.setSound(sound);
        this.setTableMode(table);
    }

    public Integer[] getxLabel() {
        return xLabel;
    }

    public Integer[] getyLabel() {
        return yLabel;
    }

    public Integer[] getxNames() {
        return xNames;
    }

    public Integer[] getyNames() {
        return yNames;
    }

    public int getAngp1() {
        return angp1;
    }

    public int getAngp2() {
        return angp2;
    }

    public Boolean isTableMode(){
        return tableMode;
    }

    public void setTableMode(Boolean tm){
        this.tableMode=tm;
    }

    public void setAngp1(int angp1) {
        this.angp1 = angp1;
    }

    public void setAngp2(int angp2) {
        this.angp2 = angp2;
    }

    public void changeAngles(){
        int an=0;
        if(this.angp1==0){
            an=180;
        }else{
            an=0;
        }
        this.angp1=an;
        this.angp2=an;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Boolean getSwitchPlayer() {
        return switchPlayer;
    }

    public void setSwitchPlayer(Boolean switchPlayer) {
        this.switchPlayer = switchPlayer;
    }

    public int getAngleScreen() {
        return angleScreen;
    }

    public void setAngleScreen(int angleScreen) {
        this.angleScreen = angleScreen;
    }

    public Boolean getTableMode() {
        return tableMode;
    }
}
