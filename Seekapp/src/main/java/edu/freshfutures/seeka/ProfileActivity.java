package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.freshfutures.seeka.CustomWidgets.CustomViewPager;
import edu.freshfutures.seeka.Models.CareerModel;
import edu.freshfutures.seeka.Models.CountryInterestModel;
import edu.freshfutures.seeka.Models.ProfileInfoModel;
import edu.freshfutures.seeka.Models.ProfileInterestModel;
import edu.freshfutures.seeka.Models.ProfileMainInterestModel;
import edu.freshfutures.seeka.adapters.CareerAdapter;
import edu.freshfutures.seeka.adapters.CountryInterestAdapter;
import edu.freshfutures.seeka.adapters.EduSysAdapter;
import edu.freshfutures.seeka.adapters.HobbyAdapter;
import edu.freshfutures.seeka.adapters.ProfileViewAdapter;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

public class ProfileActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ImageButton toolbarBack;
    CustomViewPager viewPager;
    TabLayout tabLayout;

    Button btnEdit;
    Button btnCancel;

    String getEmail;
    String getfname;
    String getLname;
    String getSkypeID;
    String getPhoneNo;
    String getGender;
    String getBday;
    String getOrigin;
    String getCitizenship;

    HashMap<String, String> scores;

    String getLevel;
    String getEduOrigin;
    String getEduSystem;
    String getInstitution;

    int position = 0;
    int visiblePage;

    protected static String TOKENID = "token_id";

    private static String TAG = CountryDetailsActivity.class.getSimpleName();

    public HobbyAdapter hbAdapter;
    public CountryInterestAdapter ctryAdapter;
    public CareerAdapter crerAdapter;

    ProfileInterestModel model;
    ArrayList<ProfileInterestModel> lisProfInterest;

    CountryInterestModel ctryModel;
    ArrayList<CountryInterestModel> listCtryInterest;

    CareerModel crerModel;
    ArrayList<CareerModel> listCrerInterest;

    ProfileInfoModel infoModel;
    ArrayList<ProfileInfoModel> arrInfoModel;

    ProfileMainInterestModel mainInterestModel;

    JSONArray arrayCtryInterest;
    JSONArray arrayHobbies;
    JSONArray arrayCareer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_edit_profile_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        View v = customBar.getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);

        actionBarTitle = (TextView) customView.findViewById(R.id.txtHomeTitle);
        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);
        btnEdit = (Button) customView.findViewById(R.id.btnProfileEdit);
        btnCancel = (Button) customView.findViewById(R.id.btnProfileEditCancel);

        makeJsonObjectRequest();

        setTitle("Profile");

        viewPager = (CustomViewPager) findViewById(R.id.profileviewpager);
        ProfileViewAdapter pagerAdapter = new ProfileViewAdapter(getSupportFragmentManager(), ProfileActivity.this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(2);

        Bundle extras = getIntent().getExtras();

        final ImageButton btnProfileInfo = (ImageButton) findViewById(R.id.btnProfileInfo);
        final ImageButton btnProfileEdu = (ImageButton) findViewById(R.id.btnProfileEdu);
        final ImageButton btnProfileInterests = (ImageButton) findViewById(R.id.btnProfileInterests);

        final TextView txtInfo = (TextView) findViewById(R.id.txtProfileInfo);
        final TextView txtEdu = (TextView) findViewById(R.id.txtProfileEdu);
        final TextView txtInterests = (TextView) findViewById(R.id.txtProfileInterests);

        if(extras != null) {
            position = extras.getInt("view_pager_no");
            viewPager.setCurrentItem(position, true);

            if (btnProfileInfo != null) {
                btnProfileInfo.setImageResource(R.mipmap.profile_info_image);
            }
            if (txtInfo != null) {
                txtInfo.setTextColor(Color.parseColor("#696969"));
            }

            if (btnProfileEdu != null) {
                btnProfileEdu.setImageResource(R.mipmap.profile_edu_image);
            }
            if (txtEdu != null) {
                txtEdu.setTextColor(Color.parseColor("#696969"));
            }

            btnProfileInterests.setImageResource(R.mipmap.profile_interests_image_click);
            if (txtInterests != null) {
                txtInterests.setTextColor(Color.parseColor("#00aff0"));
            }
        }

        if (btnProfileInfo != null) {
            btnProfileInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewPager.setPagingEnabled(true);

                    viewPager.setCurrentItem(0, true);
                    btnProfileInfo.setImageResource(R.mipmap.profile_info_image_clicked);
                    if (txtInfo != null) {
                        txtInfo.setTextColor(Color.parseColor("#0091f0"));
                    }

                    if (btnProfileEdu != null) {
                        btnProfileEdu.setImageResource(R.mipmap.profile_edu_image);
                    }
                    if (txtEdu != null) {
                        txtEdu.setTextColor(Color.parseColor("#696969"));
                    }

                    if (btnProfileInterests != null) {
                        btnProfileInterests.setImageResource(R.mipmap.profile_interests_image);
                    }
                    if (txtInterests != null) {
                        txtInterests.setTextColor(Color.parseColor("#696969"));
                    }

                    viewPager.setPagingEnabled(false);

                }
            });
        }

        if (btnProfileEdu != null) {
            btnProfileEdu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setPagingEnabled(true);

                    viewPager.setCurrentItem(1, true);
                    if (btnProfileInfo != null) {
                        btnProfileInfo.setImageResource(R.mipmap.profile_info_image);
                    }
                    if (txtInfo != null) {
                        txtInfo.setTextColor(Color.parseColor("#696969"));
                    }

                    btnProfileEdu.setImageResource(R.mipmap.profile_edu_image_click);
                    if (txtEdu != null) {
                        txtEdu.setTextColor(Color.parseColor("#0091f0"));
                    }

                    if (btnProfileInterests != null) {
                        btnProfileInterests.setImageResource(R.mipmap.profile_interests_image);
                    }
                    if (txtInterests != null) {
                        txtInterests.setTextColor(Color.parseColor("#696969"));
                    }

                    viewPager.setPagingEnabled(false);
                }
            });
        }

        if (btnProfileInterests != null) {
            btnProfileInterests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewPager.setPagingEnabled(true);

                    viewPager.setCurrentItem(2, true);
                    if (btnProfileInfo != null) {
                        btnProfileInfo.setImageResource(R.mipmap.profile_info_image);
                    }
                    if (txtInfo != null) {
                        txtInfo.setTextColor(Color.parseColor("#696969"));
                    }

                    if (btnProfileEdu != null) {
                        btnProfileEdu.setImageResource(R.mipmap.profile_edu_image);
                    }
                    if (txtEdu != null) {
                        txtEdu.setTextColor(Color.parseColor("#696969"));
                    }

                    btnProfileInterests.setImageResource(R.mipmap.profile_interests_image_click);
                    if (txtInterests != null) {
                        txtInterests.setTextColor(Color.parseColor("#0091f0"));
                    }

                    viewPager.setPagingEnabled(false);
                }
            });
        }

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                ProfileActivity.this.finish();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEdit.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);


                    getEmail = FragmentProfileInfo.txtEmail.getText().toString();
                    getfname = FragmentProfileInfo.txtFirstName.getText().toString();
                    getLname = FragmentProfileInfo.txtLastName.getText().toString();
                    getSkypeID = FragmentProfileInfo.txtSkypeID.getText().toString();
                    getPhoneNo = FragmentProfileInfo.txtPhoneNo.getText().toString();
                    getGender = FragmentProfileInfo.txtGender.getText().toString();
                    getBday = FragmentProfileInfo.txtBirthday.getText().toString();
                    getOrigin = FragmentProfileInfo.txtOrigin.getText().toString();
                    getCitizenship = FragmentProfileInfo.txtCitizenship.getText().toString();


                    if (getEmail.equals("-")) {
                        FragmentProfileInfo.txtUserEmail.setText("");
                    } else {
                        FragmentProfileInfo.txtUserEmail.setText(getEmail);
                    }
                    if (getfname.equals("-")) {
                        FragmentProfileInfo.txtUserFirstName.setText("");

                    } else {
                        FragmentProfileInfo.txtUserFirstName.setText(getfname);
                    }
                    if (getLname.equals("-")) {
                        FragmentProfileInfo.txtUserLastName.setText("");
                    } else {
                        FragmentProfileInfo.txtUserLastName.setText(getLname);
                    }
                    if (getGender.equals("-")) {
                        FragmentProfileInfo.txtEditGender.setText("-");
                        FragmentProfileInfo.txtEditGender.setGravity(Gravity.RIGHT);
                    } else {
                        FragmentProfileInfo.txtEditGender.setText(getGender);
                    }
                    if (getBday.equals("-")) {
                        FragmentProfileInfo.txtEditBirthday.setText("-");
                        FragmentProfileInfo.txtEditBirthday.setGravity(Gravity.RIGHT);

                    } else {
                        FragmentProfileInfo.txtEditBirthday.setText(getBday);
                    }

                    if (getOrigin.equals("-")) {
                        FragmentProfileInfo.txtEditOrigin.setText("-");
                        FragmentProfileInfo.txtEditOrigin.setGravity(Gravity.RIGHT);

                    } else {
                        FragmentProfileInfo.txtEditOrigin.setText(getOrigin);
                    }

                    if (getCitizenship.equals("-")) {
                        FragmentProfileInfo.txtEditCitizenship.setText("-");
                        FragmentProfileInfo.txtEditCitizenship.setGravity(Gravity.RIGHT);
                    } else {
                        FragmentProfileInfo.txtEditCitizenship.setText(getCitizenship);
                    }

                    if (getSkypeID.equals("-")) {
                        FragmentProfileInfo.txtUserSkypeId.setText("");

                    } else {
                        FragmentProfileInfo.txtUserSkypeId.setText(getSkypeID);
                    }

                    if (getPhoneNo.equals("-")) {
                        FragmentProfileInfo.txtUserPhoneNo.setText("");

                    } else {
                        FragmentProfileInfo.txtUserPhoneNo.setText(getPhoneNo);

                    }

                    FragmentProfileInfo.btnUpdate.setVisibility(View.VISIBLE);

                    FragmentProfileInfo.btnGender.setEnabled(true);
                    FragmentProfileInfo.btnBirthday.setEnabled(true);
                    FragmentProfileInfo.btnOrigin.setEnabled(true);
                    FragmentProfileInfo.btnCitizenship.setEnabled(true);

                    FragmentProfileInfo.txtEmail.setVisibility(View.GONE);
                    FragmentProfileInfo.txtFirstName.setVisibility(View.GONE);
                    FragmentProfileInfo.txtLastName.setVisibility(View.GONE);
                    FragmentProfileInfo.txtSkypeID.setVisibility(View.GONE);
                    FragmentProfileInfo.txtPhoneNo.setVisibility(View.GONE);

                    FragmentProfileInfo.txtUserEmail.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtUserFirstName.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtUserLastName.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtUserSkypeId.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtUserPhoneNo.setVisibility(View.VISIBLE);

                    FragmentProfileInfo.txtGender.setVisibility(View.GONE);
                    FragmentProfileInfo.txtBirthday.setVisibility(View.GONE);
                    FragmentProfileInfo.txtOrigin.setVisibility(View.GONE);
                    FragmentProfileInfo.txtCitizenship.setVisibility(View.GONE);

                    FragmentProfileInfo.txtEditGender.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtEditBirthday.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtEditOrigin.setVisibility(View.VISIBLE);
                    FragmentProfileInfo.txtEditCitizenship.setVisibility(View.VISIBLE);

                    getLevel = FragmentProfileEdu.txtLevel.getText().toString();
                    getEduOrigin = FragmentProfileEdu.txtEduLvlOrigin.getText().toString();
                    getEduSystem = FragmentProfileEdu.txtEduSystem.getText().toString();
                    getInstitution = FragmentProfileEdu.txtInstitution.getText().toString();

                    if (getLevel.equals("-")) {
                        FragmentProfileEdu.txtEditLevel.setText("-");
                        FragmentProfileEdu.txtEditLevel.setGravity(Gravity.RIGHT);
                    } else {
                        FragmentProfileEdu.txtEditLevel.setText(getLevel);
                    }

                    if (getEduOrigin.equals("-")) {
                        FragmentProfileEdu.txtEditEduLvlOrigin.setText("-");
                        FragmentProfileEdu.txtEditEduLvlOrigin.setGravity(Gravity.RIGHT);
                    } else {
                        FragmentProfileEdu.txtEditEduLvlOrigin.setText(getEduOrigin);
                    }

                    if (getEduSystem.equals("-")) {
                        FragmentProfileEdu.txtEditEduSystem.setText("-");
                        FragmentProfileEdu.txtEditEduSystem.setGravity(Gravity.RIGHT);
                    } else {
                        FragmentProfileEdu.txtEditEduSystem.setText(getEduSystem);
                    }

                    FragmentProfileEdu.btnLevel.setEnabled(true);
                    FragmentProfileEdu.btnEduLvlOrigin.setEnabled(true);
                    FragmentProfileEdu.btnEduSystem.setEnabled(true);

                    FragmentProfileInterests.btnAddInterests.setEnabled(true);
                    FragmentProfileInterests.btnAddCountries.setEnabled(true);
                    FragmentProfileInterests.btnAddCareer.setEnabled(true);

                    FragmentProfileEdu.btnUpdate.setVisibility(View.VISIBLE);

                    FragmentProfileEdu.txtLevel.setVisibility(View.GONE);
                    FragmentProfileEdu.txtEduLvlOrigin.setVisibility(View.GONE);
                    FragmentProfileEdu.txtEduSystem.setVisibility(View.GONE);
                    FragmentProfileEdu.txtInstitution.setVisibility(View.GONE);

                    FragmentProfileEdu.txtEditLevel.setVisibility(View.VISIBLE);
                    FragmentProfileEdu.txtEditEduLvlOrigin.setVisibility(View.VISIBLE);
                    FragmentProfileEdu.txtEditEduSystem.setVisibility(View.VISIBLE);
                    FragmentProfileEdu.txtEditInstitution.setVisibility(View.VISIBLE);

                FragmentProfileInterests.btnUpdateInterest.setVisibility(View.VISIBLE);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEdit.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);


                FragmentProfileInfo.txtEmail.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtFirstName.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtLastName.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtSkypeID.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtPhoneNo.setVisibility(View.VISIBLE);

                FragmentProfileInfo.txtUserEmail.setVisibility(View.GONE);
                FragmentProfileInfo.txtUserFirstName.setVisibility(View.GONE);
                FragmentProfileInfo.txtUserLastName.setVisibility(View.GONE);
                FragmentProfileInfo.txtUserSkypeId.setVisibility(View.GONE);
                FragmentProfileInfo.txtUserPhoneNo.setVisibility(View.GONE);

                FragmentProfileInfo.btnUpdate.setVisibility(View.GONE);

                FragmentProfileInfo.txtGender.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtBirthday.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtOrigin.setVisibility(View.VISIBLE);
                FragmentProfileInfo.txtCitizenship.setVisibility(View.VISIBLE);

                FragmentProfileInfo.txtEditGender.setVisibility(View.GONE);
                FragmentProfileInfo.txtEditBirthday.setVisibility(View.GONE);
                FragmentProfileInfo.txtEditOrigin.setVisibility(View.GONE);
                FragmentProfileInfo.txtEditCitizenship.setVisibility(View.GONE);


                FragmentProfileEdu.txtLevel.setVisibility(View.VISIBLE);
                FragmentProfileEdu.txtEduLvlOrigin.setVisibility(View.VISIBLE);
                FragmentProfileEdu.txtEduSystem.setVisibility(View.VISIBLE);
                FragmentProfileEdu.txtInstitution.setVisibility(View.VISIBLE);

                FragmentProfileEdu.txtEditLevel.setVisibility(View.GONE);
                FragmentProfileEdu.txtEditEduLvlOrigin.setVisibility(View.GONE);
                FragmentProfileEdu.txtEditEduSystem.setVisibility(View.GONE);
                FragmentProfileEdu.txtEditInstitution.setVisibility(View.GONE);

                FragmentProfileEdu.btnUpdate.setVisibility(View.GONE);
                FragmentProfileInterests.btnUpdateInterest.setVisibility(View.GONE);

                FragmentProfileInfo.btnGender.setEnabled(false);
                FragmentProfileInfo.btnBirthday.setEnabled(false);
                FragmentProfileInfo.btnOrigin.setEnabled(false);
                FragmentProfileInfo.btnCitizenship.setEnabled(false);

                FragmentProfileEdu.btnLevel.setEnabled(false);
                FragmentProfileEdu.btnEduLvlOrigin.setEnabled(false);
                FragmentProfileEdu.btnEduSystem.setEnabled(false);

                FragmentProfileInterests.btnAddInterests.setEnabled(false);
                FragmentProfileInterests.btnAddCountries.setEnabled(false);
                FragmentProfileInterests.btnAddCareer.setEnabled(false);

            }
        });

    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_PROFILE_DATA;
        SharedPreferences getSession = getSharedPreferences(getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String sessionID = getSession.getString(TOKENID, "");


        //String requestURL = Url.URL_PROFILE_DATA;
        JSONObject obj = new JSONObject();

        try {
            obj.put("sessionToken", sessionID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                arrInfoModel = new ArrayList<>();

                try {

                    String status = response.getString("status");

                    if(status.equals("1")) {

                        infoModel = new ProfileInfoModel();
                        mainInterestModel = new ProfileMainInterestModel();

                        // Profile Info Data....
                        JSONObject objData = response.getJSONObject("data");
                        JSONObject userInfo = objData.getJSONObject("userInfo");

                        String emailResult = objData.getString("email");
                        String firstNameResult = userInfo.getString("firstName");
                        String lastNameResult = userInfo.getString("lastName");
                        String skypeIDResult = userInfo.getString("skypeId");
                        String phoneNoResult = userInfo.getString("mobileNo");

                        String genderResult = userInfo.getString("gender");
                        String bdayResult = userInfo.getString("dob");
                        String originResult = userInfo.getString("coutOfOrign");
                        String citizenshipResult = userInfo.getString("citizenship");

                        infoModel.setUserEmail(emailResult);
                        infoModel.setUserFName(firstNameResult);
                        infoModel.setUserLName(lastNameResult);
                        infoModel.setUserSkypeId(skypeIDResult);
                        infoModel.setUserPhone(phoneNoResult);

                        infoModel.setUserGender(genderResult);
                        infoModel.setUserDOB(bdayResult);
                        infoModel.setUserOrigin(originResult);
                        infoModel.setUserCitizenship(citizenshipResult);

                         //Profile Education Data....
                        JSONObject userEdu = objData.getJSONObject("userEducation");

                        String subjectGrades;

                        // Profile Interest Data....
                        JSONObject userInterests = objData.getJSONObject("userInterest");
                        arrayCtryInterest = userInterests.getJSONArray("countryIntrests");
                        arrayHobbies = userInterests.getJSONArray("hobbies");
                        arrayCareer = userInterests.getJSONArray("careerIntrests");

                        mainInterestModel.setUserCtryInterest(arrayCtryInterest);
                        mainInterestModel.setUserHobbies(arrayHobbies);
                        mainInterestModel.setUserCareerInterest(arrayCareer);

                        displayUserInfoDetails();
                        displayUserInterestDetails();

                        String eduLevel = userEdu.getString("eduLevel");
                        String eduCountry = userEdu.getString("eduCountry");
                        String eduSystem = userEdu.getString("eduSystem");
                        String eduInstitution = userEdu.getString("eduInst");
                        String gpaScore = userEdu.getString("gpaScore");


                        if(eduLevel != null) {
                            if(eduLevel.equals("") || userEdu.isNull("eduLevel")) {
                                FragmentProfileEdu.txtLevel.setText("-");
                            }
                            else {
                                FragmentProfileEdu.txtLevel.setText(eduLevel);
                            }

                        }

                        if(eduCountry != null) {
                            if(eduCountry.equals("") || userEdu.isNull("eduCountry")) {
                                FragmentProfileEdu.txtEduLvlOrigin.setText("-");
                            }
                            else {
                                FragmentProfileEdu.txtEduLvlOrigin.setText(eduCountry);
                            }

                        }

                        if(eduSystem != null) {
                            if(eduSystem.equals("") || userEdu.isNull("eduSystem")) {
                                FragmentProfileEdu.txtEduSystem.setText("-");
                            }
                            else {
                                FragmentProfileEdu.txtEduSystem.setText(eduSystem);
                            }

                        }

                        if(eduInstitution != null) {
                            if(eduInstitution.equals("") || userEdu.isNull("eduInst")) {
                                FragmentProfileEdu.txtInstitution.setText("-");
                            }
                            else {
                                FragmentProfileEdu.txtInstitution.setText(eduInstitution);
                            }

                        }


                        if(gpaScore != null) {
                            if(gpaScore.equals("") || userEdu.isNull("gpaScore")) {
                                FragmentProfileEdu.txtgpaScore.setText("-");
                            }
                            else {
                                FragmentProfileEdu.txtgpaScore.setText(gpaScore);
                            }

                        }

                        FragmentProfileEdu.setEduSysTypeVisible(eduSystem);

                        if(FragmentProfileEdu.listGradesBody.getVisibility() == View.VISIBLE) {

                            JSONObject userSubGrades = userEdu.getJSONObject("cambrigeSubGrds");
                            JSONObject gradesSubjects = userSubGrades.getJSONObject("subjects");

                            if(FragmentProfileEdu.listGradesBody.getVisibility() == View.GONE) {
                                FragmentProfileEdu.listGradesBody.setVisibility(View.VISIBLE);
                            }

                            if(FragmentProfileEdu.singleGradesBody.getVisibility() == View.VISIBLE) {
                                FragmentProfileEdu.singleGradesBody.setVisibility(View.GONE);
                            }

                            Iterator subjectKeys = gradesSubjects.keys();
                            scores = new HashMap<>();

                            while(subjectKeys.hasNext()) {

                                String subject = (String)subjectKeys.next();

                                subjectGrades = gradesSubjects.getString(subject);
                                scores.put(subject, subjectGrades);

                            }

                            String firstSubject = (String)scores.keySet().toArray()[0];
                            String secondSubject = (String)scores.keySet().toArray()[1];
                            String thirdSubject = (String)scores.keySet().toArray()[2];
                            String fourthSubject = (String)scores.keySet().toArray()[3];
                            String fifthSubject = (String)scores.keySet().toArray()[4];

                            String grade1 = scores.get(firstSubject);
                            String grade2 = scores.get(secondSubject);
                            String grade3 = scores.get(thirdSubject);
                            String grade4 = scores.get(fourthSubject);
                            String grade5 = scores.get(fifthSubject);

                            FragmentProfileEdu.txtgradeTitle1.setText(firstSubject);
                            FragmentProfileEdu.txtgradeTitle2.setText(secondSubject);
                            FragmentProfileEdu.txtgradeTitle3.setText(thirdSubject);
                            FragmentProfileEdu.txtgradeTitle4.setText(fourthSubject);
                            FragmentProfileEdu.txtgradeTitle5.setText(fifthSubject);

                            FragmentProfileEdu.txtgrade1.setText(grade1);
                            FragmentProfileEdu.txtgrade2.setText(grade2);
                            FragmentProfileEdu.txtgrade3.setText(grade3);
                            FragmentProfileEdu.txtgrade4.setText(grade4);
                            FragmentProfileEdu.txtgrade5.setText(grade5);

                        }
                        if(FragmentProfileEdu.singleGradesBody.getVisibility() == View.VISIBLE) {

                            int systemScore = userEdu.getInt("eduSystemScore");

                            if(FragmentProfileEdu.singleGradesBody.getVisibility() == View.GONE) {
                                FragmentProfileEdu.singleGradesBody.setVisibility(View.VISIBLE);
                            }

                            if(FragmentProfileEdu.listGradesBody.getVisibility() == View.VISIBLE) {
                                FragmentProfileEdu.listGradesBody.setVisibility(View.GONE);
                            }

                            FragmentProfileEdu.txtsingleScore.setText(String.valueOf(systemScore));

                        }


                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                            LayoutInflater inflaters = (ProfileActivity.this).getLayoutInflater();

                            View convertView = inflaters.inflate(R.layout.fragment_nationality, null);
                            builder.setView(convertView);

                            FragmentProfileEdu.btnNltyCancel = (Button) convertView.findViewById(R.id.btnCancelNationality);
                            FragmentProfileEdu.btnNltyOk = (Button) convertView.findViewById(R.id.btnAcceptNationality);

                            FragmentProfileEdu.eduSysList = new ArrayList<>();
                            FragmentProfileEdu.mapss = new HashMap<>();

                            try {

                                JSONArray obj = new JSONArray(loadEduJSONFromAsset());
                                ArrayList<String> eduSysName = new ArrayList<>();

                                SharedPreferences getJsonCtryCode = getSharedPreferences("JSONCTRYCODEPREF", Context.MODE_PRIVATE);
                                JSONObject jsonCountryCode = new JSONObject(getJsonCtryCode.getString("JSONCTRYCODE", "{}"));

                                String str = jsonCountryCode.getString(FragmentProfileEdu.txtEduLvlOrigin.getText().toString());

                                for (int i = 0; i < obj.length(); i++) {

                                    JSONObject currObj = obj.getJSONObject(i);
                                    String edu_ctry_code = currObj.getString("country_code");

                                    if(edu_ctry_code.equals("00") || edu_ctry_code.equals(str)) {

                                        eduSysName.add(currObj.getString("sys_edu_txt"));
                                        FragmentProfileEdu.mapss.put(FragmentProfileEdu.CTRY_NAME, eduSysName);
                                        FragmentProfileEdu.eduSysList.add(FragmentProfileEdu.mapss);

                                    }

                                }

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ListView lisCountry = (ListView) convertView.findViewById(R.id.lisNationality);
                            FragmentProfileEdu.adptsys = new EduSysAdapter(ProfileActivity.this, FragmentProfileEdu.eduSysList);

                            lisCountry.setAdapter(FragmentProfileEdu.adptsys);

                            lisCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    FragmentProfileEdu.adptsys.setSelectedIndex(position);
                                    FragmentProfileEdu.eduSystems = FragmentProfileEdu.eduSysList.get(position).get(FragmentProfileEdu.CTRY_NAME).get(position);
                                    FragmentProfileEdu.adptsys.notifyDataSetChanged();


                                }
                            });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void displayUserInfoDetails() {

        getEmail = infoModel.getUserEmail();
        getfname = infoModel.getUserFName();
        getLname = infoModel.getUserLName();
        getSkypeID = infoModel.getUserSkypeId();
        getPhoneNo = infoModel.getUserPhone();
        getGender = infoModel.getUserGender();
        getBday = infoModel.getUserDOB();
        getOrigin = infoModel.getUserOrigin();
        getCitizenship = infoModel.getUserCitizenship();

       // Toast.makeText(getActivity(), getEmail + " " + getSkypeID + " " + getOrigin, Toast.LENGTH_LONG).show();

        if(getEmail != null) {
            if(getEmail.equals("")) {
                FragmentProfileInfo.txtEmail.setText("-");
            }
            else {
                FragmentProfileInfo.txtEmail.setText(getEmail);


            }

        }

        if(getfname != null) {
            if(getfname.equals("")) {
                FragmentProfileInfo.txtFirstName.setText("-");
            }
            else {
                FragmentProfileInfo.txtFirstName.setText(getfname);
            }

        }

        if(getLname != null) {
            if(getLname.equals("")) {
                FragmentProfileInfo.txtLastName.setText("-");
            }
            else {
                FragmentProfileInfo.txtLastName.setText(getLname);
            }

        }

        if(getSkypeID != null) {
            if(getSkypeID.equals("")) {
                FragmentProfileInfo.txtSkypeID.setText("-");
            }
            else {
                FragmentProfileInfo.txtSkypeID.setText(getSkypeID);
            }

        }

        if(getPhoneNo != null) {
            if(getPhoneNo.equals("")) {
                FragmentProfileInfo.txtPhoneNo.setText("-");
            }
            else {
                FragmentProfileInfo.txtPhoneNo.setText(getPhoneNo);
            }

        }

        if(getGender != null) {
            if(getGender.equals("")) {
                FragmentProfileInfo.txtGender.setText("-");
            }
            else {
                FragmentProfileInfo.txtGender.setText(getGender);
            }

        }

        if(getBday != null) {
            if(getBday.equals("")) {
                FragmentProfileInfo.txtBirthday.setText("-");
            }
            else {
                FragmentProfileInfo.txtBirthday.setText(getBday);
            }

        }

        if(getOrigin != null) {
            if(getOrigin.equals("")) {
                FragmentProfileInfo.txtOrigin.setText("-");
            }
            else {
                FragmentProfileInfo.txtOrigin.setText(getOrigin);
            }
        }

        if(getCitizenship != null) {
            if(getCitizenship.equals("")) {
                FragmentProfileInfo.txtCitizenship.setText("-");
            }
            else {
                FragmentProfileInfo.txtCitizenship.setText(getCitizenship);
            }

        }

    }

    public void displayUserInterestDetails() {

        lisProfInterest = new ArrayList<>();
        listCtryInterest = new ArrayList<>();
        listCrerInterest = new ArrayList<>();

        for(int i = 0; i < arrayHobbies.length(); i++) {

            model = new ProfileInterestModel();
            try {
                model.setHobbyInterest(arrayHobbies.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            lisProfInterest.add(model);

        }

        for(int i = 0; i < arrayCtryInterest.length(); i++) {
            ctryModel = new CountryInterestModel();
            try {
                ctryModel.setCtryInterest(arrayCtryInterest.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listCtryInterest.add(ctryModel);
        }

        for(int i = 0; i < arrayCareer.length(); i++) {
            crerModel = new CareerModel();
            try {
                crerModel.setCareerInterest(arrayCareer.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listCrerInterest.add(crerModel);
        }

        hbAdapter = new HobbyAdapter(ProfileActivity.this, lisProfInterest);
        FragmentProfileInterests.hobbyRecyclerView.setAdapter(hbAdapter);

        ctryAdapter = new CountryInterestAdapter(ProfileActivity.this, listCtryInterest);
        FragmentProfileInterests.ctryRecyclerView.setAdapter(ctryAdapter);

        crerAdapter = new CareerAdapter(ProfileActivity.this, listCrerInterest);
        FragmentProfileInterests.careerRecyclerView.setAdapter(crerAdapter);

    }

    public String loadEduJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("edusystem/edu_sys_code.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
