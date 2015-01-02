package it.polimi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

import com.polimi.core.R;

import it.polimi.game.model.Game;
import it.polimi.game.model.SettingsHandler;

public class SettingsActivity extends Activity {
    private NumberPicker np;
    private ToggleButton musicB,soundB,animationsB;
    private Button saveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        SettingsHandler.getInstance().saveStatistic(musicB.isChecked(),soundB.isChecked(),animationsB.isChecked(),np.getValue());
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
