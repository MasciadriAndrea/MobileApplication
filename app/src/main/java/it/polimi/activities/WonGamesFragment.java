package it.polimi.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.polimi.core.R;

import java.util.ArrayList;
import java.util.List;

import it.polimi.game.model.Player;
import it.polimi.game.model.PlayerHandler;

/**
 * Created by Paolo on 29/12/2014.
 */
public class WonGamesFragment extends Fragment {

    private PlayerHandler playerHandler;
    private ListView lv;
    public List<ListItem> items ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stat, container, false);
        lv =  (ListView)rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO order ListItems list
        playerHandler = PlayerHandler.getInstance();
        List<Player> listPlayers = playerHandler.getPlayers();
        this.items = new ArrayList<ListItem>();
        ArrayList<String> names = new ArrayList<String>();
        for (Player player : listPlayers){
            if (player.getPlayedGames() != 0){
                Integer wonPerc = player.getWonGames()/player.getPlayedGames();
                names.add(player.getName());
                items.add(new ListItem(player.getName(),wonPerc));
            }

        }



        //StableArrayAdapter adapter = new StableArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,names);
        lv.setAdapter(new StableArrayAdapter());
    }

    private class StableArrayAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = getLayoutInflater(getArguments()).inflate(
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
}
