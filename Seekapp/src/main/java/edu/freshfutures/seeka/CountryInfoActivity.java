package edu.freshfutures.seeka;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.CountryListAdapter;
import edu.freshfutures.seeka.adapters.CustomAdapter;

public class CountryInfoActivity extends AppCompatActivity {

    private Activity mCtx;

    public static final String COUNTRY_BACKGROUND = "country_background";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_IMAGE = "country_icons";

    static CountryListAdapter cdpt;
    static HashMap<String, int[]> map;
    static ArrayList<HashMap<String, int[]>> countryList = null;

    ListView listCtry;

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ImageButton toolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        View v = customBar.getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);

        actionBarTitle = (TextView) findViewById(R.id.txtHomeTitle);
        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);

        setTitle("Countries");

        listCtry = (ListView) findViewById(R.id.lisCountries);

        countryList = new ArrayList<>();

        mCtx = CountryInfoActivity.this;

        int[] CountryBackground = new int[] {R.mipmap.search_australia, R.mipmap.search_austria, R.mipmap.search_brazil,
                R.mipmap.search_canada, R.mipmap.search_china, R.mipmap.search_czech_republic, R.mipmap.search_denmark,
                R.mipmap.search_finland, R.mipmap.search_france, R.mipmap.search_germany, R.mipmap.search_iceland,
                R.mipmap.search_india, R.mipmap.search_ireland, R.mipmap.search_italy, R.mipmap.search_japan,
                R.mipmap.search_malaysia, R.mipmap.search_mexico, R.mipmap.search_netherlands,
                R.mipmap.search_new_zealand, R.mipmap.search_poland, R.mipmap.search_russia,
                R.mipmap.search_singapore, R.mipmap.search_slovenia, R.mipmap.search_south_africa,
                R.mipmap.search_spain, R.mipmap.search_sweden, R.mipmap.search_switzerland,
                R.mipmap.search_u_a_e, R.mipmap.search_u_s_a, R.mipmap.search_united_kingdom};

        int[] CountryLists = new int[] {R.string.country_australia, R.string.country_austria, R.string.country_brazil,
                R.string.country_canada, R.string.country_china, R.string.country_czech, R.string.country_denmark,
                R.string.country_finland, R.string.country_france, R.string.country_germany, R.string.country_iceland,
                R.string.country_india, R.string.country_ireland, R.string.country_italy, R.string.country_japan,
                R.string.country_malaysia, R.string.country_mexico, R.string.country_netherlands, R.string.country_new_zealand,
                R.string.country_poland, R.string.country_russia, R.string.country_singapore,
                R.string.country_slovenia, R.string.country_south_africa, R.string.country_spain,
                R.string.country_sweden, R.string.country_switzerland, R.string.country_u_a_e, R.string.country_u_s_a,
                R.string.country_uk};

        int[] MonumentListImg = new int[] {R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.austria_building, R.mipmap.austria_building,
                R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building, R.mipmap.sydney_building, R.mipmap.austria_building,
                R.mipmap.austria_building};


        map = new HashMap<>();

        for(int i=0; i < CountryLists.length; i++) {
            map.put(COUNTRY_BACKGROUND, CountryBackground);
            map.put(COUNTRY_IMAGE, MonumentListImg);
            map.put(COUNTRY_NAME, CountryLists);

            countryList.add(map);
        }

        cdpt = new CountryListAdapter(mCtx, countryList);
        listCtry.setAdapter(cdpt);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                CountryInfoActivity.this.finish();
            }
        });
    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }
}
