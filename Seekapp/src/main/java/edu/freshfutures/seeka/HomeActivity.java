package edu.freshfutures.seeka;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import edu.freshfutures.seeka.interfaces.ToolBarBack;


public class HomeActivity extends AppCompatActivity implements SortDialogFragment.NoticeDialogListener, ToolBarBack {

    private String tags;
    TabLayout bottomTab;

    private FragmentHome fragmentHome;
    private FragmentSearch fragmentSearch;
    private FragmentUnlock fragmentUnlock;
    private FragmentMenu fragmentMenu;


    FragmentManager fm;
    FragmentTransaction ft;

    private String tabTitles[] = new String[] {"Home", "Search", "Unlocked", "Menu"};
    private int[] imageResId = new int[] {R.mipmap.home_icon, R.mipmap.bottom_search_icon, R.mipmap.home_unlock_icon,
            R.mipmap.menu_icon};

    String result;
    int pageLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentHome = FragmentHome.newInstance(0, "Home");
        fragmentSearch = FragmentSearch.newInstance(1, "Search");
        fragmentUnlock = FragmentUnlock.newInstance(2, "Unlock");
        fragmentMenu = FragmentMenu.newInstance(3, "Menu");

        bottomTab = (TabLayout) findViewById(R.id.bottom_tab);
        assert bottomTab != null;

        final TabLayout.Tab home = bottomTab.newTab();
        final TabLayout.Tab search = bottomTab.newTab();
        final TabLayout.Tab unlock = bottomTab.newTab();
        final TabLayout.Tab menu = bottomTab.newTab();

        bottomTab.addTab(home, 0);
        bottomTab.addTab(search, 1);
        bottomTab.addTab(unlock, 2);
        bottomTab.addTab(menu, 3);

        for(int i=0; i < bottomTab.getTabCount(); i++) {
            TabLayout.Tab tab = bottomTab.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }

            else {
                System.out.println("Cannot set custom view for tab!");
            }
        }



        SharedPreferences getFirstLogged = getSharedPreferences("PREFFIRSTLOGGED", Context.MODE_PRIVATE);
        int firstLoggedStatus = getFirstLogged.getInt("FIRST_LOGGED", 0);


        if(findViewById(R.id.frame_container) != null) {

            if (savedInstanceState != null) {
                result= (String) savedInstanceState.getSerializable("SEARCH_RESULT");
                return;
            }


            if(firstLoggedStatus == 0) {

                Bundle extras = getIntent().getExtras();
                if(extras != null) {

                    pageLoaded = extras.getInt("tab_no");

                    if(pageLoaded == 1) {
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.frame_container, fragmentSearch, "SEARCH_FRAGMENT").commit();
                    }
                    else {
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.frame_container, fragmentHome, "HOME_FRAGMENT").commit();
                    }

                    SharedPreferences settings = getSharedPreferences("PREFFIRSTLOGGED", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("FIRST_LOGGED", 1);
                    editor.apply();

                }

            }

            else {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_container, fragmentHome, "HOME_FRAGMENT").commit();

                TabLayout.Tab tabs = bottomTab.getTabAt(0);

                View homeViews = null;
                if (tabs != null) {
                    homeViews = tabs.getCustomView();
                }
                if (homeViews != null) {

                    TextView barTitle = (TextView) homeViews.findViewById(R.id.txtMenuIcon);
                    ImageView tabIcon = (ImageView) homeViews.findViewById(R.id.imgBtnMenu);

                    tabIcon.setImageResource(R.mipmap.home_active_icon);
                    barTitle.setTextColor(Color.parseColor("#00aff0"));

                }
            }

        }


        bottomTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                View views = tab.getCustomView();

                TextView barTitle = null;
                ImageView tabIcon = null;
                if (views != null) {
                    barTitle = (TextView) views.findViewById(R.id.txtMenuIcon);
                    tabIcon = (ImageView) views.findViewById(R.id.imgBtnMenu);
                }


                if (barTitle != null) {
                    barTitle.setTextColor(Color.parseColor("#00aff0"));
                }

                switch (tab.getPosition()) {
                    case 0:

                        if (tabIcon != null) {
                            tabIcon.setImageResource(R.mipmap.home_active_icon);
                        }

                        ft.replace(R.id.frame_container, fragmentHome, "HOME_FRAGMENT");
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                        break;

                    case 1:

                        if (tabIcon != null) {
                            tabIcon.setImageResource(R.mipmap.search_active_icon);
                        }

                        ft.replace(R.id.frame_container, fragmentSearch, "SEARCH_FRAGMENT");
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                        break;

                    case 2:

                        if (tabIcon != null) {
                            tabIcon.setImageResource(R.mipmap.unlock_active_icon);
                        }

                        ft.replace(R.id.frame_container, fragmentUnlock, "UNLOCKED_FRAGMENT");
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                        break;

                    case 3:

                        if (tabIcon != null) {
                            tabIcon.setImageResource(R.mipmap.menu_active_icon);
                        }

                        ft.replace(R.id.frame_container, fragmentMenu, "MENU_FRAGMENT");
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View views = tab.getCustomView();
                TextView barTitle = null;
                ImageView tabIcon = null;
                if (views != null) {
                    barTitle = (TextView) views.findViewById(R.id.txtMenuIcon);
                    tabIcon = (ImageView) views.findViewById(R.id.imgBtnMenu);
                }

                if (tab.getPosition() == 0) {
                    if (tabIcon != null) {
                        tabIcon.setImageResource(R.mipmap.home_icon);
                    }
                } else if (tab.getPosition() == 1) {
                    if (tabIcon != null) {
                        tabIcon.setImageResource(R.mipmap.bottom_search_icon);
                    }

                } else if (tab.getPosition() == 2) {
                    if (tabIcon != null) {
                        tabIcon.setImageResource(R.mipmap.home_unlock_icon);
                    }

                } else {
                    if (tabIcon != null) {
                        tabIcon.setImageResource(R.mipmap.menu_icon);
                    }
                }


                if (barTitle != null) {
                    barTitle.setTextColor(Color.parseColor("#646969"));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_bottom_tab, null);

        TextView barTitle = (TextView) v.findViewById(R.id.txtMenuIcon);
        ImageView tabIcon = (ImageView) v.findViewById(R.id.imgBtnMenu);

        barTitle.setText(tabTitles[position]);
        tabIcon.setImageResource(imageResId[position]);

        return v;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("country/country_code.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void switchBackVisible(int vs, int ivs) {

    }

    @Override
    public void switchBackInvisible(int vs, int ivs) {

    }

    @Override
    public void stringTag(String t) {
        this.tags = t;
    }

    public String getStringTag() {
        return this.tags;
    }

    @Override
    public void onDialogConfirmClick(DialogFragment dialog) {
        Fragment f = getSupportFragmentManager().findFragmentByTag("unlockFragment");

        if(f instanceof FragmentUnlock) {
            ((FragmentUnlock) f).changeOnConfirm();
        }
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {

        Fragment f = getSupportFragmentManager().findFragmentByTag("unlockFragment");

        if(f instanceof FragmentUnlock) {
            ((FragmentUnlock) f).changeOnCancel();
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HomeActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }


}
