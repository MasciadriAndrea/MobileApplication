package it.polimi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ToggleButton;

import com.polimi.core.R;

import it.polimi.core.Assets;
import it.polimi.core.GameMainActivity;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;
import it.polimi.game.model.SettingsHandler;


public class PauseActivity extends Activity {

    private ToggleButton musicB,soundB;
    private Button quit,resume,restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pause);
        musicB = (ToggleButton) findViewById(R.id.musicToggleButton);
        musicB.setChecked(Game.getInstance().getMusic());
        musicB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               SettingsHandler.getInstance().saveSettings(musicB.isChecked(), Game.getInstance().getSound(), Game.getInstance().getGraphic(),Game.getInstance().getTableMode(),
                       Game.getInstance().getnSeeds());

            }
        });

        soundB = (ToggleButton) findViewById(R.id.soundsToggleButton);
        soundB.setChecked(Game.getInstance().getSound());
        soundB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsHandler.getInstance().saveSettings(Game.getInstance().getMusic(), soundB.isChecked(), Game.getInstance().getGraphic(),Game.getInstance().getTableMode(),
                        Game.getInstance().getnSeeds());

            }
        });

        quit = (Button)findViewById(R.id.quitBtn);
        resume = (Button)findViewById(R.id.resumeBtn);
        restart = (Button)findViewById(R.id.restartBtn);
    }

    public void onRestartBtn(View arg){
        Player p1=Game.getInstance().getGh().getP1();
        Player p2=Game.getInstance().getGh().getP2();
        Game.getInstance().getGameActivity().finish();
        Boolean isFast=Game.getInstance().getGh().getIsFastGame();
        if(!Game.getInstance().getGh().getIsHH()){
            //game human vs megabrain
            Game.getInstance().setGh(new GameHandler(p1,isFast));
        }else{
            //game human vs human
            Game.getInstance().setGh(new GameHandler(p1,p2,isFast));
        }
        Intent i = new Intent(Game.getInstance().getMenuActivity(),GameMainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void onQuitBtn(View arg){
        Game.getInstance().getGameActivity().finish();
        Game.getInstance().setGameActivity(null);
        this.finish();
    }

    public void onResumeBtn(View arg){
        this.finish();
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
