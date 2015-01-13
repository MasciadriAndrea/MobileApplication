package it.polimi.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.polimi.core.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.polimi.core.Assets;
import it.polimi.game.model.BestMoveResult;
import it.polimi.game.model.BestMovesHandler;
import it.polimi.game.model.Player;
import it.polimi.game.model.PlayerHandler;

public class StatisticResActivity  extends Activity {
    private PlayerHandler playerHandler;
    private ViewFlipper viewFlipper;
    private ListView listview2;
    public List<ListItem> items2 ;
    public Button b1,b2,b3;
    private float lastX;
    // Tab titles
    private String[] tabs = {"","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistic_res);
        Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/ahronbd.ttf");
        TextView tv=(TextView) findViewById(R.id.textView);
        tv.setTypeface(type);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),StatisticsActivity.class);
                StatisticResActivity.this.finish();
                startActivity(i);
            }
        });

        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),StatisticMovActivity.class);
                StatisticResActivity.this.finish();
                startActivity(i);
            }
        });

        /*b2.setPressed(true);b2.setTextColor(Color.parseColor("#fcaa3c"));
        b1.setPressed(false);b1.setTextColor(Color.parseColor("#3d210d"));
        b3.setPressed(false);b3.setTextColor(Color.parseColor("#3d210d"));*/
        listview2=(ListView) findViewById(R.id.listView2);
        playerHandler = PlayerHandler.getInstance();
        List<Player> listPlayers = playerHandler.getPlayers();

        //-------------------------------------------lv2

        Collections.sort(listPlayers, new PlayerComparator2());

        this.items2 = new ArrayList<ListItem>();
        ArrayList<String> names = new ArrayList<String>();
        for (Player player : listPlayers){
            //TODO here we can exclude megabrain's statistics
            Double maxScore = player.getMaxScoreResult();
            names.add(player.getName());
            items2.add(new ListItem(player.getName(),maxScore.intValue()));

        }
        listview2.setAdapter(new StableArrayAdapter());

    }

    public void onclick1(View arg){
        Intent i = new Intent(getBaseContext(),StatisticsActivity.class);
        this.finish();
        startActivity(i);
    }

    public void onclick3(View arg){
        Intent i = new Intent(getBaseContext(),StatisticMovActivity.class);
        this.finish();
        startActivity(i);
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



    private class StableArrayAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = getLayoutInflater( ).inflate(
                        R.layout.statistic_row, null);
            }
            ListItem stat = (ListItem) getItem(position);

            TextView nameTextView = (TextView)
                    convertView.findViewById(R.id.player_name);
            TextView valueTextView = (TextView)
                    convertView.findViewById(R.id.value);

            nameTextView.setText(stat.getPlayerName());
            valueTextView.setText(String.valueOf(stat.getValue()));

            return convertView;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public Object getItem(int position)
        {
            return items2.get(position);
        }

        @Override
        public int getCount()
        {
            return items2.size();
        }

    }



    private class PlayerComparator2 implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return p2.getMaxScoreResult().compareTo(p1.getMaxScoreResult());
        }
    }


}


