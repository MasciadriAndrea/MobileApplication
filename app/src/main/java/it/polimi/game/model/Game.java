package it.polimi.game.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
    private Integer nSeeds;
    private Integer sizeBowl;

    protected Game() {
        seeds=new ArrayList<Seed>();
        playable=true;
        assets = null;
        this.gh=null;
        gameActivity=null;
        menuActivity= null;
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

    public void saveSettings(Boolean music, Boolean sound, Boolean animations, Integer nseeds){
        this.setGraphic(animations);
        this.setMusic(music);
        this.setnSeeds(nseeds);
        this.setSound(sound);
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
}
