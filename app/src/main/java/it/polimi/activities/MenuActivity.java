package it.polimi.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.polimi.core.R;

import it.polimi.core.GameMainActivity;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

public class MenuActivity extends Activity {
    private Button b_single,b_multi, b_statistics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        b_single = (Button) findViewById(R.id.button_single);
        b_multi = (Button) findViewById(R.id.button_multi);
        b_statistics =(Button) findViewById(R.id.button_statistic);
        b_single.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),ChoosePlayerActivity.class);
                i.putExtra("player", 0);
                i.putExtra("isSinglePlayer",true);
                startActivity(i);
            }
        });

        b_multi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),ChoosePlayerActivity.class);
                i.putExtra("player", 0);
                i.putExtra("isSinglePlayer",false);
                startActivity(i);
            }
        });

        b_statistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(getBaseContext(),StatisticsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
