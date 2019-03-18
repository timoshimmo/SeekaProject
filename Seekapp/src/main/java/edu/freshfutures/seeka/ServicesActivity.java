package edu.freshfutures.seeka;

import android.content.Context;
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

import edu.freshfutures.seeka.adapters.ServicesAdapter;

public class ServicesActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ListView listInterests;
    Context context;
    static String[] interestTitles;
    public static int[] interestIcons;
    public static int[] selectedIcons;
    String[] imageDescription;
    public static final ArrayList<Integer> interestsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Services");

        ImageButton confirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        interestTitles = getResources().getStringArray(R.array.interests_text_array);
        interestIcons = new int[]{R.mipmap.accommodation_services, R.mipmap.accounting_services, R.mipmap.airport_pickup_services,
                R.mipmap.bus_services, R.mipmap.childcare_centre_services, R.mipmap.counseling_personal_and_academic_services,
                R.mipmap.cultural_inclusion_anti_racism_services, R.mipmap.disability_support_services, R.mipmap.employment_and_career_development_services,
                R.mipmap.health_services, R.mipmap.housing_services, R.mipmap.legal_services, R.mipmap.medical_services,
                R.mipmap.sport_teams_services, R.mipmap.sports_center_services, R.mipmap.study_library_support_services,
                R.mipmap.swimming_pool_services, R.mipmap.technology_services, R.mipmap.train_services};

        selectedIcons = new int[]{R.mipmap.accommodation_services_selected, R.mipmap.accounting_services_selected,
                R.mipmap.airport_pickup_services_selected, R.mipmap.bus_services_selected,
                R.mipmap.childcare_centre_services_selected, R.mipmap.counseling_personal_and_academic_services_selected,
                R.mipmap.cultural_inclusion_anti_racism_services_selected, R.mipmap.disability_support_services_selected,
                R.mipmap.employment_and_career_development_services_selected, R.mipmap.health_services_selected,
                R.mipmap.housing_services_selected, R.mipmap.legal_services_selected, R.mipmap.medical_services_selected,
                R.mipmap.sport_teams_services_selected, R.mipmap.sports_center_services_selected,
                R.mipmap.study_library_support_services_selected, R.mipmap.swimming_pool_services_selected,
                R.mipmap.technology_services_selected, R.mipmap.train_services_selected};

        imageDescription = new String[]{"accomodation_unselected", "calculator", "plane",
                "interest_bus_unselected", "child_care_unselected", "academic_counseling_unselected",
                "cultural_inclusion_unselected", "wheelchair_person", "briefcase", "medic_heart",
                "housing_services_unselected", "legal_services_selected", "mdeical_services_unselected",
                "sports_team_unselected", "sports_center_unselected", "study_library_support_unselected",
                "swimming_pool_unselected", "technology_services_unselected", "train_services_unselected"};

        context = ServicesActivity.this;


        listInterests = (ListView) findViewById(R.id.listViewInterests);
        listInterests.setAdapter(new ServicesAdapter(context, interestTitles, interestIcons));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("SERVICES_IMAGES", interestsList);
                setResult(RESULT_OK, intent);
                ServicesActivity.this.finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
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
