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
import android.util.Log;
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

public class StatisticsActivity  extends Activity {
    private PlayerHandler playerHandler;
    private ListView listview;
    public List<ListItem> items;
    public Button b1,b2,b3,b4,back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);
        Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/ahronbd.ttf");
        TextView tv=(TextView) findViewById(R.id.textView);
        tv.setTypeface(type);
        back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StatisticsActivity.this.finish();
            }
        });
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        b1.setTypeface(type);

        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),StatisticResActivity.class);
                startActivity(i);
                StatisticsActivity.this.finish();
            }
        });
        b2.setTypeface(type);
        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),StatisticMovActivity.class);
                startActivity(i);
                StatisticsActivity.this.finish();
            }
        });
        b3.setTypeface(type);
        b4 = (Button) findViewById(R.id.buttonH);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),HistoryActivity.class);
                startActivity(i);
                StatisticsActivity.this.finish();
            }
        });
        b4.setTypeface(type);
        listview = (ListView) findViewById(R.id.listView);


        //-------------------------------------------lv1
        playerHandler = PlayerHandler.getInstance();
        List<Player> listPlayers = playerHandler.getPlayers();
        Collections.sort(listPlayers, new PlayerComparator());

        this.items = new ArrayList<ListItem>();
        for (Player player : listPlayers) {
            if (player.getPlayedGames() != 0) {
                Integer wonPerc = (player.getWonGames() * 100 / player.getPlayedGames());
                items.add(new ListItem(player.getName(), wonPerc));
            }

        }
        listview.setAdapter(new StableArrayAdapter());
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
            return items.get(position);
        }

        @Override
        public int getCount()
        {
            return items.size();
        }

    }

    private class PlayerComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            Integer p1perc,p2perc;
            if(!p1.getPlayedGames().equals(0)){
                p1perc = p1.getWonGames() * 100/p1.getPlayedGames();
            }else{
                p1perc=0;
            }
            if(!p2.getPlayedGames().equals(0)) {
                p2perc = p2.getWonGames() * 100 / p2.getPlayedGames();
            }else{
                p2perc=0;
            }
            return  p2perc.compareTo(p1perc);
        }
    }


}


