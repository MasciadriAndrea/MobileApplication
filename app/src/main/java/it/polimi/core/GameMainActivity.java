package it.polimi.core;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import it.polimi.game.model.Game;

public class GameMainActivity extends Activity {
	public static final int GAME_WIDTH = 1920;
	public static final int GAME_HEIGHT = 1200;
	public static GameView sGame;
	public static AssetManager assets;
	private static SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getPreferences(Activity.MODE_PRIVATE);
		assets = Game.getInstance().getAssets();
		sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Game.getInstance().setGameActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Assets.onResume();
		sGame.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Assets.onPause();
		sGame.onPause();
	}

    @Override
    public void onBackPressed() {
        this.finish();
        Log.v("GameMainActivity","destroy gameMainActivity");
    }
	
	// Write methods to allow access to prefs from other states.
}
