package it.polimi.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polimi.core.R;

import it.polimi.core.Assets;

public class StatisticsActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);
        tabs[0]=  getResources().getString(R.string.statTab1);
        tabs[1]=  getResources().getString(R.string.statTab2);
        tabs[2]=  getResources().getString(R.string.statTab3);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        //Requires API 14 as minimum
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setCustomView(R.layout.action_bar_stat);

        // Adding Tabs

        //settare titolo nel layout, un layout per ciascuna tab
        for (String tab_name : tabs) {
        Tab newTab = actionBar.newTab().setTabListener(this);
            RelativeLayout tabView = (RelativeLayout) LinearLayout.inflate(this, R.layout.tab_view, null);
            TextView tvTabTitle = (TextView) tabView.findViewById(R.id.tab_title);
            tvTabTitle.setText(tab_name);
            newTab.setCustomView(tabView);

            actionBar.addTab(newTab);
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
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
}


