package edu.freshfutures.seeka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

public class StartActivity extends AppCompatActivity {

    static SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
    TextView textSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        SharedPreferences setFirstLogged = getSharedPreferences("PREFFIRSTLOGGED", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setFirstLogged.edit();
        editor.putInt("FIRST_LOGGED", 0);
        editor.apply();

        mSectionsPagerAdapter = new StartActivity.SectionsPagerAdapter(this.getSupportFragmentManager());

        mViewPager = (ViewPager)this.findViewById(R.id.launch_pager);
        textSkip = (TextView) findViewById(R.id.btnSkip);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        CirclePageIndicator cp = (CirclePageIndicator)findViewById(R.id.indicator);
        cp.setViewPager(mViewPager);

        mViewPager.setCurrentItem(0, true);

        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        if(mViewPager.getCurrentItem() == 0) {
            textSkip.setTextColor(Color.parseColor("#0091f0"));
        }
        else {
            textSkip.setTextColor(Color.parseColor("#ffffff"));
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        public int getCount() {
            return 4;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new StartActivityFragmentOne();
                case 1:
                    return new StartActivityFragmentTwo();
                case 2:
                    return new StartActivityFragmentThree();
                case 3:
                    return new StartActivityFragmentFour();
                default:
                    return null;
            }
        }

    }
}
