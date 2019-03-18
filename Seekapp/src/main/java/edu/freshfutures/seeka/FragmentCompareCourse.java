/*package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.CompareCoursesAdapter;


public class FragmentCompareCourse extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ListView lisCompareCourses;
    Context ctx;

    static ArrayList<Unidata> allData;

    static HashMap<String, ArrayList<Unidata>> map;
    static ArrayList<HashMap<String, ArrayList<Unidata>>> coursesList = null;

    public static final String UNI_NAME = "uni_name";
    public static final String COURSE_NAME = "course_name";
    public static final String UNI_LOGO = "uni_logo";

    Unidata udt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_courses_fragment);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Career");

        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        ctx = FragmentCompareCourse.this;

        setTitle("Compare Courses");

        coursesList = new ArrayList<>();
        map = new HashMap<>();

        udt = new Unidata();
        allData = new ArrayList<>();

        udt.setUniversityName("Select Course");
        udt.setCourseName("Select Course");
        udt.setUniversityLogo(R.mipmap.no_uni_logo);

        allData.add(udt);

        lisCompareCourses = (ListView) findViewById(R.id.listViewCompareCourse);
        lisCompareCourses.setAdapter(new CompareCoursesAdapter(ctx, ));

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

     /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compare_courses_fragment, container, false);



   //     map.put(UNI_NAME, allData);
   //     map.put(COURSE_NAME, courseName);
     //   map.put(UNI_LOGO, uniLogo);

     //   coursesList.add(map);

        Universities: "Waterloo University", "Memorial University of NewFoundland",
         * Courses: getActivity().getResources().getString(R.string.unlock_txt_value),
                getActivity().getResources().getString(R.string.unlock_txt_value),

           Icons: R.mipmap.delete_compare_uni_icon, R.mipmap.delete_compare_uni_icon,
          *



        return view;
    }



}*/
