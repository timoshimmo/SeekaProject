package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.freshfutures.seeka.Models.CountryInterestModel;
import edu.freshfutures.seeka.adapters.CountryInterestAdapter;
import edu.freshfutures.seeka.adapters.InterestExpListAdapter;

public class ProfileCtryInterest extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ExpandableListView expandableListView;
    InterestExpListAdapter expandableListAdapter;
    List expandableListTitle;
    HashMap expandableListChild;

    CountryInterestModel ctryModel;
    ArrayList<CountryInterestModel> listCtryInterest;

    JSONArray objArray;

    public CountryInterestAdapter ctryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ctry_interest);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Select Country");

        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableCountries);
        expandableListChild = ExpandableCtryInterestList.getData();
        expandableListTitle = new ArrayList(expandableListChild.keySet());
        expandableListAdapter = new InterestExpListAdapter(this, expandableListTitle, expandableListChild);
        expandableListView.setAdapter(expandableListAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objArray = new JSONArray();

                if(expandableListAdapter.setJsonCountryData().length() > 0) {

                    objArray = expandableListAdapter.setJsonCountryData();

                    listCtryInterest = new ArrayList<>();

                    try {

                        for (int i = 0; i < objArray.length(); i++) {
                            ctryModel = new CountryInterestModel();
                            ctryModel.setCtryInterest((String) objArray.get(i));

                            listCtryInterest.add(ctryModel);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    ctryAdapter = new CountryInterestAdapter(ProfileCtryInterest.this, listCtryInterest);
                    FragmentProfileInterests.ctryRecyclerView.setAdapter(ctryAdapter);

                    SharedPreferences settings = getSharedPreferences("CTRYINTERESTPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("CTRYINTERESTLIST", objArray.toString());
                    editor.apply();

                }

                onBackPressed();
                ProfileCtryInterest.this.finish();
            }
        });


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
    }

    public void setTitle(String title) {

        actionBarTitle = (TextView) customView.findViewById(R.id.txtFilterTitle);
        actionBarTitle.setText(title);
    }

}
