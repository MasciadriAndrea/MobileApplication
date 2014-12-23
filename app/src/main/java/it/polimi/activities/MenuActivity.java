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

    }

    public void onClickMultiPlay(View arg0){
        // Intent i = new Intent(this,ChoosePlayerActivity.class);
        Intent i = new Intent(this,GameMainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }
}
