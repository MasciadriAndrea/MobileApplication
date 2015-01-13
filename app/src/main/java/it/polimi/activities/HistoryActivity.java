package it.polimi.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.polimi.core.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.core.Assets;
import it.polimi.game.model.GameHistory;
import it.polimi.game.model.HistoryHandler;
import it.polimi.game.model.Player;

public class HistoryActivity extends Activity {
    public List<GameHistory> items;
    public Button b1, b2, b3, b4, back;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "fonts/ahronbd.ttf");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setTypeface(type);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), StatisticsActivity.class);
                startActivity(i);
                HistoryActivity.this.finish();
            }
        });
        b1.setTypeface(type);
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), StatisticResActivity.class);
                startActivity(i);
                HistoryActivity.this.finish();
            }
        });
        b2.setTypeface(type);
        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), StatisticMovActivity.class);
                startActivity(i);
                HistoryActivity.this.finish();
            }
        });
        b3.setTypeface(type);
        b4 = (Button) findViewById(R.id.buttonH);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        b4.setTypeface(type);
        listview = (ListView) findViewById(R.id.listView);
        items = HistoryHandler.getInstance().getGames();
        listview.setAdapter(new StableArrayAdapter());

    }
        @Override
        public void onPause () {
            super.onPause();
            Assets.onPause();
        }

        @Override
        public void onResume () {
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
                        R.layout.history_row, null);
            }
            GameHistory stat = (GameHistory) getItem(position);

            TextView p1Name = (TextView)
                    convertView.findViewById(R.id.p1Name);

            TextView p1score = (TextView)
                    convertView.findViewById(R.id.p1Score);
            TextView p2Name = (TextView)
                    convertView.findViewById(R.id.p2Name);

            TextView p2score = (TextView)
                    convertView.findViewById(R.id.p2Score);
            TextView dateP = (TextView)
                    convertView.findViewById(R.id.Date);

           p1Name.setText(stat.getP1().getName());
            p1score.setText(stat.getScore1().toString());
            p2Name.setText(stat.getP2().getName());
            p2score.setText(stat.getScore2().toString());
           dateP.setText(stat.getData().toString());

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
}



