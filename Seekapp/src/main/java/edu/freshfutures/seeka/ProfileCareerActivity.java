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

import edu.freshfutures.seeka.Models.CareerModel;
import edu.freshfutures.seeka.Models.ProfileInterestModel;
import edu.freshfutures.seeka.adapters.CareerAdapter;
import edu.freshfutures.seeka.adapters.CareerExpListAdapter;
import edu.freshfutures.seeka.adapters.HobbyAdapter;
import edu.freshfutures.seeka.adapters.HobbySelectAdapter;
import edu.freshfutures.seeka.adapters.InterestExpListAdapter;

public class ProfileCareerActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ExpandableListView expandableListView;
    CareerExpListAdapter expandableListAdapter;
    List expandableListTitle;
    HashMap expandableListChild;

    CareerModel crerModel;
    ArrayList<CareerModel> listCrerInterest;

    JSONArray objArray;

    public CareerAdapter crerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_career);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Select Career");

        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableCareers);
        expandableListChild = ExpandableInterestList.getData();
        expandableListTitle = new ArrayList(expandableListChild.keySet());
        expandableListAdapter = new CareerExpListAdapter(this, expandableListTitle, expandableListChild);
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

                if(expandableListAdapter.setJsonCareerData().length() > 0) {

                    objArray = expandableListAdapter.setJsonCareerData();

                    listCrerInterest = new ArrayList<>();

                    try {

                        for (int i = 0; i < objArray.length(); i++) {
                            crerModel = new CareerModel();
                            crerModel.setCareerInterest((String)objArray.get(i));

                            listCrerInterest.add(crerModel);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    crerAdapter = new CareerAdapter(ProfileCareerActivity.this, listCrerInterest);
                    FragmentProfileInterests.careerRecyclerView.setAdapter(crerAdapter);

                    SharedPreferences settings = getSharedPreferences("CAREERPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("CAREERLIST", objArray.toString());
                    editor.apply();

                }

                onBackPressed();
                ProfileCareerActivity.this.finish();
            }
        });

    }

    public void setTitle(String title) {

        actionBarTitle = (TextView) customView.findViewById(R.id.txtFilterTitle);
        actionBarTitle.setText(title);
    }


}
