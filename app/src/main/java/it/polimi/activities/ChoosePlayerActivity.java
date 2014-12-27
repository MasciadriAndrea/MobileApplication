package it.polimi.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.polimi.core.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import it.polimi.core.Assets;
import it.polimi.core.GameMainActivity;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;
import it.polimi.game.model.PlayerHandler;
import it.polimi.game.persistence.PlayerDAO;

public class ChoosePlayerActivity extends Activity {

    EditText username;
    ListView lv;
    String itemSelected;
    PlayerDAO playerDAO;
    int playerId;
    Boolean isSinglePlayer;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_player);
        Intent i=getIntent();
        playerId=i.getIntExtra("player",0);
        isSinglePlayer=i.getBooleanExtra("isSinglePlayer",false);
        playerDAO = PlayerDAO.getInstance(this);
        try {
            playerDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        username=(EditText) findViewById(R.id.editText);
        lv=(ListView) findViewById(R.id.listView);
        select=(Button) findViewById(R.id.button);

        List<Player> listPlayers = PlayerHandler.getInstance().getPlayers();
        listPlayers.remove(PlayerHandler.getInstance().getPlayerById(playerId));
        listPlayers.remove(PlayerHandler.getInstance().getPlayerById(1));//megabrain
        List<String> listNames = new ArrayList<String>();
        for (Player p : listPlayers)
            listNames.add(p.getName());

        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, listNames,listPlayers);
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
        username.clearFocus();
        String txtStr = username.getText().toString().trim();
        Boolean oldOne=false;
        Boolean isValid=false;
        Player selectedPlayer=null;
        for(Player p:PlayerHandler.getInstance().getPlayers()){
            if(p.getName().equals(txtStr)){
                oldOne=true;
                isValid=true;
                selectedPlayer=p;
                Log.v("choosePlayerActivity","nella lista ha trovato il player che si chiama"+ selectedPlayer.getName());
            }
        }
        if((!txtStr.equals(""))&&(!oldOne)){
            selectedPlayer=playerDAO.addPlayer(txtStr);
            Log.v("choosePlayerActivity","entrato ad aggiungere il player chiamato"+ selectedPlayer.getName());
            PlayerHandler.getInstance().updateList();
            isValid=true;
        }
        if(isValid){
            if(isSinglePlayer){
                //start game versus megabrain
                Player p1=selectedPlayer;
                Game.getInstance().setGh(new GameHandler(p1));
                Intent i = new Intent(this,GameMainActivity.class);
                startActivity(i);
                finish();
            }else{
                //multiplayer
                if(playerId == 0){
                    //ask for player 2
                    Intent i = new Intent(this,ChoosePlayerActivity.class);
                    i.putExtra("player", selectedPlayer.getId());
                    i.putExtra("isSinglePlayer",false);
                    startActivity(i);
                    finish();
                }else{
                    //start multiplayer game
                    Player p1=PlayerHandler.getInstance().getPlayerById(playerId);
                    Player p2=selectedPlayer;
                    Game.getInstance().setGh(new GameHandler(p1,p2));
                    Intent i = new Intent(this,GameMainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }
        select.clearFocus();
        //TODO
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Insert a name!").setTitle("Bzzz");
        AlertDialog dialog = builder.create();
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        LinkedHashMap<String, Integer> mIdMap = new LinkedHashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects, List<Player> listPlayers) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                //here instead i put the id of the player
                mIdMap.put(objects.get(i),listPlayers.get(i).getId() );
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


    @Override
    public void onBackPressed() {
    }




}
