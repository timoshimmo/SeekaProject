package edu.freshfutures.seeka;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterCountriesActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List expandableListTitle;
    HashMap expandableListDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_countries);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Country Selection");

        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableCountryList.getData();
        expandableListTitle = new ArrayList(expandableListDetail.keySet());
        expandableListAdapter = new edu.freshfutures.seeka.adapters.ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                TextView listTitleTextView = (TextView) v
                        .findViewById(R.id.txtCountryGroup);
                TextView listSubTitleTextView = (TextView) v
                        .findViewById(R.id.txtCountrySubtitle);

                listTitleTextView.setTextColor(Color.parseColor("#0091f0"));
                listSubTitleTextView.setTextColor(Color.parseColor("#0091f0"));

                return false;
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void setTitle(String title) {

        actionBarTitle = (TextView) customView.findViewById(R.id.txtFilterTitle);
        actionBarTitle.setText(title);
    }


}
