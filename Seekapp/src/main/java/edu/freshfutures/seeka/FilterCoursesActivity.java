package edu.freshfutures.seeka;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import edu.freshfutures.seeka.adapters.FilterCourseAdapter;

public class FilterCoursesActivity extends AppCompatActivity {

    ListView lisCoures;

    public String[] allCourses;
    ArrayList<String> coursesList = new ArrayList<>();

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    FilterCourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_courses);

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

        setTitle("Course Keywords");

        lisCoures = (ListView) findViewById(R.id.lisFilterCourses);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);
        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);

        allCourses = getResources().getStringArray(R.array.filter_courses_added_array);
        Collections.addAll(coursesList, allCourses);

        adapter = new FilterCourseAdapter(FilterCoursesActivity.this, coursesList);
        lisCoures.setAdapter(adapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.setAllCourses();
                Intent intent = new Intent(FilterCoursesActivity.this, FilterActivity.class);
                startActivity(intent);
                FilterCoursesActivity.this.finish();
            }
        });
    }

    public void setTitle(String title) {

        actionBarTitle.setText(title);

    }

}
