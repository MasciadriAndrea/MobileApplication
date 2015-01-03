package it.polimi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.polimi.core.R;

import it.polimi.core.Assets;
import it.polimi.game.model.Game;
import it.polimi.game.model.PlayerHandler;
import it.polimi.game.model.SettingsHandler;

public class LoadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load);
        Game.getInstance().setAssets(getAssets());
        Game.getInstance().setLoadActivity(this);
        SettingsHandler.getInstance().settingsInitialization();
        Assets.load();
        Intent i = new Intent(this,MenuActivity.class);
        startActivity(i);
        PlayerHandler.getInstance();
        // Commented not kill this activity used for context
        //this.finish();
    }
}
