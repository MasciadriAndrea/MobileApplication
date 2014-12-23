package it.polimi.game.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

public class Game {
    private static Game instance = null;
    private GameHandler gh;
    private Activity gameActivity;
    private AssetManager assets;
    protected Game() {
        assets = null;
        this.gh=null;
        gameActivity=null;
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
}
