package it.polimi.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.polimi.core.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.polimi.game.model.Player;
import it.polimi.game.model.PlayerHandler;

public class BestScoresFragment extends Fragment {

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

        playerHandler = PlayerHandler.getInstance();
        List<Player> listPlayers = playerHandler.getPlayers();
        Collections.sort(listPlayers, new PlayerComparator());

        this.items = new ArrayList<ListItem>();
        ArrayList<String> names = new ArrayList<String>();
        for (Player player : listPlayers){
            //TODO here we can exclude megabrain's statistics
                Double maxScore = player.getMaxScoreResult();
                names.add(player.getName());
                items.add(new ListItem(player.getName(),maxScore.intValue()));

        }
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

    private class PlayerComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return p2.getMaxScoreResult().compareTo(p1.getMaxScoreResult());
        }
    }
}
