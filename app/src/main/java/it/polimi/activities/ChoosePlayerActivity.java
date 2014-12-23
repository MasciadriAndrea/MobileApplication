package it.polimi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.polimi.core.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChoosePlayerActivity extends Activity {

    EditText username;
    ListView lv;
    String itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_player);
        username=(EditText) findViewById(R.id.editText);
        lv=(ListView) findViewById(R.id.listView);

        // fill the array of values by a query over the player ordered by last game
        String[] values = new String[] { "Paolo", "Andrea","Anna", "Giovanni" };
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                lv.setItemChecked(position,true);
                final String item = (String) parent.getItemAtPosition(position);
                itemSelected = item;
                username.setText(itemSelected);
            };
        });
    }

    public void onClickB(View arg0){
       /* String txtStr = username.getText().toString();
        Intent i = new Intent(this,a2.class);
        if (txtStr != "")
            i.putExtra("username", txtStr);
        else if ((itemSelected != null))
            i.putExtra("username",itemSelected);
        startActivity(i);*/
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                //here instead i put the id of the player
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
