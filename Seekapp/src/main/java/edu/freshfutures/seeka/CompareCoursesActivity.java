package edu.freshfutures.seeka;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.freshfutures.seeka.adapters.CompareCoursesAdapter;
import edu.freshfutures.seeka.interfaces.ToolBarBack;

public class CompareCoursesActivity extends AppCompatActivity implements ToolBarBack {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ListView lisCompareCourses;
    Context ctx;

    public ArrayList<Unidata> allData;
    public HashSet<Unidata> removeDuplicates;

    static HashMap<String, Object> map;
    static ArrayList<HashMap<String, Object>> coursesList = null;

    public static final String UNI_NAME = "uni_name";
    public static final String COURSE_NAME = "course_name";
    public static final String UNI_LOGO = "uni_logo";

    Unidata udt;

    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;
    View addBodyUnderline;

    private String tags;

    ImageButton addCourse;

    CompareCoursesAdapter adpt;

    int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_courses_fragment);

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

        setTitle("Compare Courses");

        ctx = CompareCoursesActivity.this;

        addCourse = (ImageButton) findViewById(R.id.btnAddCourse);
        addBodyUnderline = findViewById(R.id.actionBar_underline);


        allData = new ArrayList<>();
        removeDuplicates = new HashSet<>();

        lisCompareCourses = (ListView) findViewById(R.id.listViewCompareCourse);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        dialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                switchBackInvisible(vs, ivs);

                String fragTag = getStringTag();
                DialogFragment dialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag(fragTag);

                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }

                setTitle("Compare Courses");

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

        addCourse.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CompareCoursesActivity.this, CompareCoursesListActivity.class);
                startActivityForResult(intent, REQUEST_CODE, null);

            }
        });

    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

    public void removeItem(int index) {

        allData.remove(index);
        adpt.notifyDataSetChanged();
        adpt.notifyDataSetInvalidated();
    }

    @Override
    public void switchBackVisible(int vs, int ivs) {

        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(toolbarBack.getVisibility() == vs) {
            toolbarBack.setVisibility(ivs);
            dialogBack.setVisibility(vs);
        }

    }

    @Override
    public void switchBackInvisible(int vs, int ivs) {

        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(dialogBack.getVisibility() == vs) {
            dialogBack.setVisibility(ivs);
            toolbarBack.setVisibility(vs);
        }

    }

    @Override
    public void stringTag(String t) {
        this.tags = t;
    }

    public String getStringTag() {
        return this.tags;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE)
        {

            String uni_name = data.getStringExtra("UNINAME");
            String uni_course = data.getStringExtra("UNICOURSE");
            int uni_logo = data.getIntExtra("UNILOGO", R.mipmap.no_uni_logo);

            udt = new Unidata(uni_name, uni_course, uni_logo);

            allData.add(udt);

            adpt = new CompareCoursesAdapter(ctx, allData);
            lisCompareCourses.setAdapter(adpt);

            removeDuplicates.addAll(allData);
            allData.clear();
            allData.addAll(removeDuplicates);

            adpt.notifyDataSetChanged();

            if(addBodyUnderline.getVisibility() == View.GONE) {
                addBodyUnderline.setVisibility(View.VISIBLE);
            }

        }
    }
}
