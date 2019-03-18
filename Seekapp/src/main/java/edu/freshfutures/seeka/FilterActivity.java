package edu.freshfutures.seeka;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.freshfutures.seeka.adapters.FacultyAdapter;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

public class FilterActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    int REQUEST_CODE = 2;
    public int[] interestIcons;
    public int[] facultyIcons;

    ArrayList<Integer> facultyList;

    FacultyAdapter adpter;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;
    ImageView img6;

    LinearLayout btnChangeUser;
    ImageView userProfile;
    TextView txtuserStatus;
    TextView txtProfileRedirect;

    LinearLayout btnFoundation;
    LinearLayout btnDiploma;
    LinearLayout btnUndergraduate;
    LinearLayout btnPostGraduate;
    LinearLayout btnOther1;
    LinearLayout btnOther2;

    ImageView imgFoundation;
    ImageView imgDiploma;
    ImageView imgUnderGrad;
    ImageView imgPostGrad;
    ImageView imgOther1;
    ImageView imgOther2;

    TextView txtFoundation;
    TextView txtDiploma;
    TextView txtUnderGrad;
    TextView txtPostGrad;
    TextView txtOther1;
    TextView txtOther2;

    TextView txtCourses;

    JSONArray arrCourses;

    private static String TAG = FilterActivity.class.getSimpleName();
    protected static String TOKENID = "token_id";

    RecyclerView lisFaculty;
    String status;

    JSONArray arrFaculties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

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

        setTitle("Filter");

        interestIcons = new int[]{R.mipmap.accommodation_services_selected, R.mipmap.accounting_services_selected,
                R.mipmap.airport_pickup_services_selected, R.mipmap.bus_services_selected,
                R.mipmap.childcare_centre_services_selected, R.mipmap.counseling_personal_and_academic_services_selected};


        ImageButton btnAddCountries = (ImageButton) findViewById(R.id.btnAddCountries);
        ImageButton btnAddServices = (ImageButton) findViewById(R.id.btnAddServices);
        ImageButton btnAddFaculty = (ImageButton) findViewById(R.id.btnAddFaculty);
        ImageButton btnAddCourses = (ImageButton) findViewById(R.id.btnAddCouses);

        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);
        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);

        btnChangeUser = (LinearLayout) findViewById(R.id.btnProfileStatus);
        userProfile = (ImageView) findViewById(R.id.imgFilterUser);
        txtuserStatus = (TextView) findViewById(R.id.txtProfileStatus);
        //txtProfileRedirect = (TextView) findViewById(R.id.txtFilterProfileMsg);

        btnFoundation = (LinearLayout) findViewById(R.id.imgFoundationLayout);
        btnDiploma = (LinearLayout) findViewById(R.id.imgDiplomaLayout);
        btnUndergraduate = (LinearLayout) findViewById(R.id.imgUGradLayout);
        btnPostGraduate = (LinearLayout) findViewById(R.id.imgPGradLayout);
        btnOther1 = (LinearLayout) findViewById(R.id.imgOther1Layout);
        btnOther2 = (LinearLayout) findViewById(R.id.imgOther2Layout);

        imgFoundation = (ImageView) findViewById(R.id.imgFoundation);
        imgDiploma = (ImageView) findViewById(R.id.imgDiploma);
        imgUnderGrad = (ImageView) findViewById(R.id.imgUGrad);
        imgPostGrad = (ImageView) findViewById(R.id.imgPGrad);
        imgOther1 = (ImageView) findViewById(R.id.imgOther1);
        imgOther2 = (ImageView) findViewById(R.id.imgOther2);

        txtFoundation = (TextView) findViewById(R.id.txtFoundation);
        txtDiploma = (TextView) findViewById(R.id.txtDiploma);
        txtUnderGrad = (TextView) findViewById(R.id.txtUGrad);
        txtPostGrad = (TextView) findViewById(R.id.txtPGrad);
        txtOther1 = (TextView) findViewById(R.id.txtOther1);
        txtOther2 = (TextView) findViewById(R.id.txtOther2);

        txtCourses = (TextView) findViewById(R.id.txtCourses);

        img1 = (ImageView) findViewById(R.id.imgServices1);
        img2 = (ImageView) findViewById(R.id.imgServices2);
        img3 = (ImageView) findViewById(R.id.imgServices3);
        img4 = (ImageView) findViewById(R.id.imgServices4);
        img5 = (ImageView) findViewById(R.id.imgServices5);
        img6 = (ImageView) findViewById(R.id.imgServices6);

        SharedPreferences getFaculties = getSharedPreferences("PREFFACULTYSELECTION", Context.MODE_PRIVATE);
        String faculties = getFaculties.getString("fscultySelections", "[]");

        facultyList = new ArrayList<>();

        lisFaculty = (RecyclerView) findViewById(R.id.lisFacultyIcons);
        lisFaculty.setLayoutManager(new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.HORIZONTAL, false));

        lisFaculty.setHasFixedSize(true);
        lisFaculty.setItemAnimator(new DefaultItemAnimator());

        if(faculties.equals("[]")) {

            Integer[] facultyIcons = {R.mipmap.agriculture_artboard_selected, R.mipmap.architecture_building_plan_artboard_selected,
                    R.mipmap.aviation_artboard_selected, R.mipmap.business_finance_accounting_artboard_selected, R.mipmap.computer_science_it_artboard_selected,
                    R.mipmap.creative_arts_design_artboard_selected, R.mipmap.education_artboard_selected, R.mipmap.engineering_artboard_selected,
                    R.mipmap.humanities_social_science_artboard_selected, R.mipmap.language_literature_artboard_selected, R.mipmap.law_artboard_selected,
                    R.mipmap.mass_communication_media_artboard_selected, R.mipmap.medical_nursing_artboard_selected, R.mipmap.science_health_science_artboard_selected,
                    R.mipmap.tourism_hotel_management_artboard_selected};

            Collections.addAll(facultyList, facultyIcons);

            adpter = new FacultyAdapter(FilterActivity.this, facultyList);
        }

        else {

            try {
                arrFaculties = new JSONArray(faculties);

                for(int i=0; i < arrFaculties.length(); i++) {
                    facultyList.add(arrFaculties.getInt(i));
                }

                adpter = new FacultyAdapter(FilterActivity.this, facultyList);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        lisFaculty.setAdapter(adpter);

        SharedPreferences getCourses = getSharedPreferences("PREFCOURSESFILTER", Context.MODE_PRIVATE);
        String coursesFilter = getCourses.getString("filterCourses", "[]");

        if(!coursesFilter.equals("[]")) {
            StringBuilder sb = new StringBuilder();

            try {
                arrCourses = new JSONArray(coursesFilter);

                for(int i=0; i < arrCourses.length(); i++) {

                    String data  = arrCourses.get(i).toString();
                    sb.append(data).append(" ");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String allCourses = sb.toString();

            txtCourses.setText(allCourses);
        }


        btnAddCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, FilterCountriesActivity.class);
                startActivity(intent);

            }
        });

        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, ServicesActivity.class);
                startActivityForResult(intent, REQUEST_CODE, null);
                //startActivity(intent);

            }
        });

        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, FacultyActivity.class);
                startActivity(intent);

            }
        });

        btnAddCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, FilterCoursesActivity.class);
                startActivity(intent);

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

                Intent intent = new Intent(FilterActivity.this, HomeActivity.class);
                startActivity(intent);
                FilterActivity.this.finish();

            }
        });

        btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtuserStatus.getCurrentTextColor() == Color.parseColor("#00aff0")) {

                    userProfile.setImageResource(R.mipmap.user_off);
                    txtuserStatus.setTextColor(Color.parseColor("#646969"));
                    txtuserStatus.setText("Profile- Off");

                    SharedPreferences settings = getSharedPreferences("PREFPROFILESTATUS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("profStatus", 0);
                    editor.apply();

                    if(btnFoundation.isEnabled()) {
                        btnFoundation.setEnabled(false);

                        if(txtFoundation.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgFoundation.setImageResource(R.mipmap.edu_level_default);
                            txtFoundation.setTextColor(Color.parseColor("#646969"));
                        }

                    }
                    if(btnDiploma.isEnabled()) {
                        btnDiploma.setEnabled(false);

                        if(txtDiploma.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgDiploma.setImageResource(R.mipmap.edu_level_default);
                            txtDiploma.setTextColor(Color.parseColor("#646969"));
                        }

                    }
                    if(btnUndergraduate.isEnabled()) {
                        btnUndergraduate.setEnabled(false);

                        if(txtUnderGrad.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgUnderGrad.setImageResource(R.mipmap.edu_level_default);
                            txtUnderGrad.setTextColor(Color.parseColor("#646969"));
                        }

                    }
                    if(btnPostGraduate.isEnabled()) {
                        btnPostGraduate.setEnabled(false);

                        if(txtPostGrad.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgPostGrad.setImageResource(R.mipmap.edu_level_default);
                            txtPostGrad.setTextColor(Color.parseColor("#646969"));
                        }

                    }
                    if(btnOther1.isEnabled()) {
                        btnOther1.setEnabled(false);

                        if(txtOther1.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgOther1.setImageResource(R.mipmap.edu_level_default);
                            txtOther1.setTextColor(Color.parseColor("#646969"));
                        }

                    }
                    if(btnOther2.isEnabled()) {
                        btnOther2.setEnabled(false);

                        if(txtOther2.getCurrentTextColor() == Color.parseColor("#0091f0")) {
                            imgOther2.setImageResource(R.mipmap.edu_level_default);
                            txtOther2.setTextColor(Color.parseColor("#646969"));
                        }

                    }

                    status = "0";

                }

                else {

                    userProfile.setImageResource(R.mipmap.user_on);
                    txtuserStatus.setTextColor(Color.parseColor("#00aff0"));
                    txtuserStatus.setText("Profile- On");

                    SharedPreferences settings = getSharedPreferences("PREFPROFILESTATUS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("profStatus", 1);
                    editor.apply();

                    if(!btnFoundation.isEnabled()) {
                        btnFoundation.setEnabled(true);
                    }
                    if(!btnDiploma.isEnabled()) {
                        btnDiploma.setEnabled(true);
                    }
                    if(!btnUndergraduate.isEnabled()) {
                        btnUndergraduate.setEnabled(true);
                    }
                    if(!btnPostGraduate.isEnabled()) {
                        btnPostGraduate.setEnabled(true);
                    }
                    if(!btnOther1.isEnabled()) {
                        btnOther1.setEnabled(true);
                    }
                    if(!btnOther2.isEnabled()) {
                        btnOther2.setEnabled(true);
                    }

                    status = "1";

                }

                makeJsonObjectRequest();

            }
        });

     /*   txtProfileRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        }); */

        btnFoundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtFoundation.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgFoundation.setImageResource(R.mipmap.edu_level_selected);
                    txtFoundation.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgFoundation.setImageResource(R.mipmap.edu_level_default);
                    txtFoundation.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnDiploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtDiploma.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgDiploma.setImageResource(R.mipmap.edu_level_selected);
                    txtDiploma.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgDiploma.setImageResource(R.mipmap.edu_level_default);
                    txtDiploma.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnUndergraduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtUnderGrad.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgUnderGrad.setImageResource(R.mipmap.edu_level_selected);
                    txtUnderGrad.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgUnderGrad.setImageResource(R.mipmap.edu_level_default);
                    txtUnderGrad.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnPostGraduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPostGrad.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgPostGrad.setImageResource(R.mipmap.edu_level_selected);
                    txtPostGrad.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgPostGrad.setImageResource(R.mipmap.edu_level_default);
                    txtPostGrad.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnOther1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtOther1.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgOther1.setImageResource(R.mipmap.edu_level_selected);
                    txtOther1.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgOther1.setImageResource(R.mipmap.edu_level_default);
                    txtOther1.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnOther2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtOther2.getCurrentTextColor() == Color.parseColor("#646969")) {
                    imgOther2.setImageResource(R.mipmap.edu_level_selected);
                    txtOther2.setTextColor(Color.parseColor("#0091f0"));
                }

                else {
                    imgOther2.setImageResource(R.mipmap.edu_level_default);
                    txtOther2.setTextColor(Color.parseColor("#646969"));
                }

            }
        });

        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, FacultyActivity.class);
                startActivity(intent);
            }
        });

        btnAddCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCourse = new Intent(FilterActivity.this, FilterCoursesActivity.class);
                startActivity(intentCourse);
            }
        });

    }

    public void setTitle(String title) {

        actionBarTitle.setText(title);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK)
        {
            ArrayList<Integer> images = data.getIntegerArrayListExtra("SERVICES_IMAGES");

            if(images.size() > 0) {

                if(images.size() == 1) {
                    img1.setImageResource(images.get(0));
                }

                if(images.size() == 2) {

                    img1.setImageResource(images.get(0));
                    img2.setImageResource(images.get(1));
                }

                if(images.size() == 3) {

                    img1.setImageResource(images.get(0));
                    img2.setImageResource(images.get(1));
                    img3.setImageResource(images.get(2));
                }

                if(images.size() == 4) {

                    img1.setImageResource(images.get(0));
                    img2.setImageResource(images.get(1));
                    img3.setImageResource(images.get(2));
                    img4.setImageResource(images.get(3));
                }

                if(images.size() == 5) {
                    img1.setImageResource(images.get(0));
                    img2.setImageResource(images.get(1));
                    img3.setImageResource(images.get(2));
                    img4.setImageResource(images.get(3));
                    img5.setImageResource(images.get(4));
                }

                if(images.size() == 6) {

                    img1.setImageResource(images.get(0));
                    img2.setImageResource(images.get(1));
                    img3.setImageResource(images.get(2));
                    img4.setImageResource(images.get(3));
                    img5.setImageResource(images.get(4));
                    img6.setImageResource(images.get(5));
                }

            }

        }
    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_PROFILE_STATUS;

        JSONObject obj = new JSONObject();

        SharedPreferences getSession = getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");


        try {

            obj.put("sessionToken", session);
            obj.put("status", status);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                // Toast.makeText(FilterActivity.this, response.toString(), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
