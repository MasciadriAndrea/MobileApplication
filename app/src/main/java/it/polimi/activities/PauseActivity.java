package it.polimi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.polimi.core.R;

import it.polimi.core.Assets;
import it.polimi.core.GameMainActivity;
import it.polimi.game.model.Game;
import it.polimi.game.model.SettingsHandler;

/**
 * Created by Paolo on 03/01/2015.
 */
public class PauseActivity extends Activity {

    private ToggleButton musicB,soundB;
    private Button quit,resume,restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        musicB = (ToggleButton) findViewById(R.id.musicToggleButton);
        musicB.setChecked(Game.getInstance().getMusic());
        musicB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Game.getInstance().saveStatistic(musicB.isChecked(),Game.getInstance().getSound(),Game.getInstance().getGraphic(),
                        Game.getInstance().getnSeeds());

            }
        });

        soundB = (ToggleButton) findViewById(R.id.soundsToggleButton);
        soundB.setChecked(Game.getInstance().getSound());
        soundB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Game.getInstance().saveStatistic(Game.getInstance().getMusic(),soundB.isChecked(),Game.getInstance().getGraphic(),
                        Game.getInstance().getnSeeds());

            }
        });

        quit = (Button)findViewById(R.id.quitBtn);
        quit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO look that if we use getBaseContext the multiplayer activity will start why?
                Context ctx = Game.getInstance().getGameActivity();
                Intent i = new Intent(ctx,MenuActivity.class);
                startActivity(i);
            }
        });

        resume = (Button)findViewById(R.id.resumeBtn);
        resume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),GameMainActivity.class);
                startActivity(i);
            }
        });

        restart = (Button)findViewById(R.id.restartBtn);
        quit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO we need to finishGameMainActivity and recreate it
                Intent i = new Intent(getBaseContext(),MenuActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.onPause();
        this.finish();
    }




}
