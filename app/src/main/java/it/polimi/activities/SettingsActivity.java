package it.polimi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

import com.polimi.core.R;

import it.polimi.core.Assets;
import it.polimi.game.model.Game;
import it.polimi.game.model.SettingsHandler;

public class SettingsActivity extends Activity {
    private NumberPicker np;
    private ToggleButton musicB,soundB,animationsB;
    private Button saveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        musicB = (ToggleButton) findViewById(R.id.musicb);
        musicB.setChecked(Game.getInstance().getMusic());

        soundB = (ToggleButton) findViewById(R.id.soundb);
        soundB.setChecked(Game.getInstance().getSound());

        animationsB = (ToggleButton) findViewById(R.id.animationsb);
        animationsB.setChecked(Game.getInstance().getGraphic());

        saveB = (Button) findViewById(R.id.saveb);

        np = (NumberPicker) findViewById(R.id.np);
        np.setMaxValue(5);
        np.setMinValue(3);
        np.setValue(Game.getInstance().getnSeeds());

        saveB.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void onSave(View arg){
        SettingsHandler.getInstance().saveSettings(musicB.isChecked(), soundB.isChecked(), animationsB.isChecked(), np.getValue());
        if(!musicB.isChecked()){
            Assets.onPause();
        }else{
            Assets.onResume();
        }
        this.finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        Assets.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        Assets.onResume();
    }
}
