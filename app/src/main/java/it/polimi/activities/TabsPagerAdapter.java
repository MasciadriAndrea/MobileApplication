package it.polimi.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Paolo on 29/12/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                // Top Rated fragment activity
                return new WonGamesFragment();
            case 1:
                // Games fragment activity
                return new BestScoresFragment();
            case 2:
                // Movies fragment activity
                return new BestMovesFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
