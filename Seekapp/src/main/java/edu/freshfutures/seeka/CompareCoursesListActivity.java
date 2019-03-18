package edu.freshfutures.seeka;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.CompareCoursesListAdapter;


public class CompareCoursesListActivity extends AppCompatActivity {

    ListView lisComaprables;
    Context ctx;

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    public static final String UNI_NAME = "uni_name";
    public static final String COURSE_NAME = "course_name";
    public static final String LOGO_IMG = "logo_image";

    ArrayList<String> unName = new ArrayList<>();
    ArrayList<String> courseName = new ArrayList<>();

    static HashMap<String, ArrayList<String>> map;
    static ArrayList<HashMap<String, ArrayList<String>>> coursesList = null;

    CompareCoursesListAdapter ccldpt;

    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_compare_courses_list);

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

        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);
        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);
        toolSettings = (ImageButton) customView.findViewById(R.id.imgHomeMenu);
        actionBarTitle = (TextView) customView.findViewById(R.id.txtHomeTitle);


        ctx = CompareCoursesListActivity.this;
        setTitle("Select Course");

        coursesList = new ArrayList<>();
        map = new HashMap<>();

        String[] uniList = new String[] {"Waterloo University", "Memorial University of NewFoundland"};
        String[] courseList = new String[] {getResources().getString(R.string.unlock_txt_value),
                getResources().getString(R.string.unlock_txt_value)};
        int[] uniLogo = new int[] {R.mipmap.waterloo_uni_logo, R.mipmap.memorial_uni_nfl_logo};


        lisComaprables = (ListView) findViewById(R.id.lisCompareCoursesList);

        ccldpt = new CompareCoursesListAdapter(ctx, uniList, courseList, uniLogo);
        lisComaprables.setAdapter(ccldpt);

        lisComaprables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ccldpt.getNewCourse(position);
            }
        });

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        toolSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsPopup newFragments = new SettingsPopup();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "settingsDialogs");
            }
        });

    }


}
