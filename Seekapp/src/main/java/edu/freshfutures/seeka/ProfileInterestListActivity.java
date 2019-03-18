package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

import edu.freshfutures.seeka.Models.ProfileInterestModel;
import edu.freshfutures.seeka.adapters.FilterCourseAdapter;
import edu.freshfutures.seeka.adapters.HobbyAdapter;
import edu.freshfutures.seeka.adapters.HobbySelectAdapter;

public class ProfileInterestListActivity extends AppCompatActivity {


    ListView lisCoures;

    public String[] allCourses;
    ArrayList<String> coursesList = new ArrayList<>();

    public HobbyAdapter hbAdapter;

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    ProfileInterestModel model;
    ArrayList<ProfileInterestModel> lisProfInterest;

    JSONArray objArray;
    HobbySelectAdapter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_interest_list);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        actionBarTitle = (TextView) customView.findViewById(R.id.txtFilterTitle);

        setTitle("Hobbies/Interests");

        lisCoures = (ListView) findViewById(R.id.lisFilterCourses);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);
        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);

        allCourses = getResources().getStringArray(R.array.filter_hobbies_array);
        Collections.addAll(coursesList, allCourses);

        adpter = new HobbySelectAdapter(ProfileInterestListActivity.this, coursesList);

        lisCoures.setAdapter(adpter);

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

                if(adpter.setJsonData().length() > 0) {

                    objArray = adpter.setJsonData();
                    lisProfInterest = new ArrayList<>();

                    try {

                        for (int i = 0; i < objArray.length(); i++) {
                            model = new ProfileInterestModel();
                            model.setHobbyInterest((String)objArray.get(i));

                            lisProfInterest.add(model);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    hbAdapter = new HobbyAdapter(ProfileInterestListActivity.this, lisProfInterest);
                    FragmentProfileInterests.hobbyRecyclerView.setAdapter(hbAdapter);

                    SharedPreferences settings = getSharedPreferences("HOBBIESPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("HOBBYLIST", objArray.toString());
                    editor.apply();

                }

                onBackPressed();
                ProfileInterestListActivity.this.finish();
            }
        });
    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

}
