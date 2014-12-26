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

import com.polimi.core.R;

import it.polimi.core.GameMainActivity;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
    }

    public void onClickSinglePlay(View arg0){
        Intent i = new Intent(this,GameMainActivity.class);
        //i.putExtra("player", 0);
        // i.putExtra("isSinglePlayer",false);
        Player p1= new Player("Anna",2);
        Game.getInstance().setGh(new GameHandler(p1));
        startActivity(i);
    }

    public void onClickMultiPlay(View arg0){
        Intent i = new Intent(this,GameMainActivity.class);
        //i.putExtra("player", 0);
       // i.putExtra("isSinglePlayer",false);
        Player p1= new Player("Andrea",1);
        Player p2=new Player("Anna",2);
        Game.getInstance().setGh(new GameHandler(p1,p2));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }
}
