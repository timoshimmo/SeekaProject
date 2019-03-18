package edu.freshfutures.seeka;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


import edu.freshfutures.seeka.Models.CourseInfoDataModel;
import edu.freshfutures.seeka.Models.CourseInfoModel;
import edu.freshfutures.seeka.adapters.CourseInfoAdapter;
import edu.freshfutures.seeka.adapters.UniImagePageAdapter;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.volley.custom.application.AppController;


public class CourseInfoActivity extends AppCompatActivity  {

    static ViewPager mViewPager;
    Context ctx;

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;

    private String tags;

    ImageView uniLogo;

    private CourseInfoTask mAuthTask = null;
    CirclePageIndicator cp;

    private View mProgressView;

    AppBarLayout topBar;
    RecyclerView recyclerViewBody;
    PagerAdapter adapter;
    CollapsingToolbarLayout collapsingToolbarLayout;

    protected ArrayList<CourseInfoModel> cInfoModels;
    protected ArrayList<CourseInfoDataModel> dataInfoModels;
    LinearLayout btnCountryDetails;

    private State state;
    private static String TAG = CourseInfoActivity.class.getSimpleName();
    protected static String TOKENID = "token_id";

    String courseId;

    CourseInfoDataModel infoModel;
    CourseInfoDataModel courseModel;

    TextView txtWorldRanking;
    TextView txtCountry;

    DecimalFormat myFormatter;

    EduQualificationDB dbHandler;
    int countData;


    public CourseInfoActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_information_layout);

        ctx = CourseInfoActivity.this;

        mViewPager = (ViewPager) findViewById(R.id.viewPagerUniImages);
        cp = (CirclePageIndicator) findViewById(R.id.indicatorImg);

        recyclerViewBody = (RecyclerView) findViewById(R.id.courses_details_list_body);
        btnCountryDetails = (LinearLayout) findViewById(R.id.btnCountrySearch);

        mProgressView = findViewById(R.id.dot_progress_bar);
        topBar = (AppBarLayout) findViewById(R.id.topAppBar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        uniLogo = (ImageView) findViewById(R.id.imageIniLogo);

        Toolbar parent = (Toolbar) findViewById(R.id.toolbarCourses);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dbHandler = new EduQualificationDB(CourseInfoActivity.this);

        if (parent != null) {
            toolbarBack = (ImageButton) parent.findViewById(R.id.imgHomeBack);
            dialogBack = (ImageButton) parent.findViewById(R.id.btnDialogBack);
            toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);
            actionBarTitle = (TextView) parent.findViewById(R.id.txtHomeTitle);
        }

        txtWorldRanking = (TextView) findViewById(R.id.txtRankingValue);
        txtCountry = (TextView) findViewById(R.id.BtnTxtABoutCountry);
        loadCourseInfo();


    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

   public void loadCourseInfo() {
        if (mAuthTask != null) {
            return;
        }

        if(isNetworkAvailable()) {
            showProgress(true);
            mAuthTask = new CourseInfoTask();
            mAuthTask.execute((Void) null);
        }
        else {
            mAuthTask = null;
            showProgress(false);
            Toast.makeText(CourseInfoActivity.this, "Internet network not available.", Toast.LENGTH_LONG).show();

        }

    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerViewBody.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerViewBody.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerViewBody.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerViewBody.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }


    private void initDataset() {

        int[] uniImages = new int[] {R.mipmap.alburya, R.mipmap.alburyb,
                R.mipmap.alburyc, R.mipmap.alburyd, R.mipmap.alburye, R.mipmap.alburyf,
                R.mipmap.alburyg, R.mipmap.alburyh, R.mipmap.alburyi, R.mipmap.alburyj, R.mipmap.alburyk, R.mipmap.alburyl,
                R.mipmap.alburym, R.mipmap.alburyn, R.mipmap.alburyo, R.mipmap.alburyp, R.mipmap.alburyq, R.mipmap.alburyr,
                R.mipmap.alburys, R.mipmap.alburyt, R.mipmap.alburyu, R.mipmap.alburyv, R.mipmap.alburyw, R.mipmap.alburyx,
                R.mipmap.alburyy, R.mipmap.alburyz};

        adapter = new UniImagePageAdapter(CourseInfoActivity.this, uniImages);

    }


    private enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    public class CourseInfoTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            initDataset();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            mAuthTask = null;
            showProgress(false);

            Bundle extras = getIntent().getExtras();


            if (extras != null) {

                int ids = extras.getInt("courseID");
                courseId = String.valueOf(ids);

                makeJsonObjectRequest(courseId);

            }

            if (success) {

                if (collapsingToolbarLayout != null) {
                    collapsingToolbarLayout.setTitleEnabled(false);
                }


             /*   topBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                        if (verticalOffset == 0) {

                            if (actionBarTitle.getVisibility() == View.VISIBLE) {
                                actionBarTitle.setVisibility(View.INVISIBLE);
                            }
                        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                            if (actionBarTitle.getVisibility() == View.INVISIBLE) {
                                actionBarTitle.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });*/

                mViewPager.setAdapter(adapter);
                if (cp != null) {
                    cp.setViewPager(mViewPager);
                }

                mViewPager.setCurrentItem(0, true);
                Glide.with(ctx).load(R.mipmap.royal_roads_logo).crossFade().into(uniLogo);

                recyclerViewBody.setLayoutManager(new LinearLayoutManager(CourseInfoActivity.this));
                recyclerViewBody.setItemAnimator(new DefaultItemAnimator());


                toolbarBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });

                btnCountryDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CourseInfoActivity.this, CountryDetailsActivity.class);
                        startActivity(intent);
                    }
                });

            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void makeJsonObjectRequest(final String courseId) {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/SchoolService/courseDetail";
        JSONObject obj = new JSONObject();

        SharedPreferences getSession = getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        cInfoModels = new ArrayList<>();
        dataInfoModels = new ArrayList<>();

        try {
            obj.put("sessionId", session);
            obj.put("courseId", courseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    cInfoModels.add(new CourseInfoModel("Course Information", CourseInfoModel.COURSE_INFO_TYPE));
                    cInfoModels.add(new CourseInfoModel("Twinning Program", CourseInfoModel.TWINNING_TYPE));
                    cInfoModels.add(new CourseInfoModel("Minimum Requirements", CourseInfoModel.MINIMUM_REQ_TYPE));
                    cInfoModels.add(new CourseInfoModel("Total Available Jobs", CourseInfoModel.TOTAL_AVAILABLE_TYPE));
                    cInfoModels.add(new CourseInfoModel(null, CourseInfoModel.MATCH_TYPE));
                    cInfoModels.add(new CourseInfoModel(null, CourseInfoModel.TOUR_TYPE));
                    cInfoModels.add(new CourseInfoModel("Scholarship Availability", CourseInfoModel.SCHOLARSHIP_TYPE));
                    cInfoModels.add(new CourseInfoModel("University Information", CourseInfoModel.UNI_INFO_TYPE));
                    cInfoModels.add(new CourseInfoModel("Services", CourseInfoModel.SERVICES_TYPE));
                    cInfoModels.add(new CourseInfoModel("Visa", CourseInfoModel.VISA_TYPE));
                    cInfoModels.add(new CourseInfoModel("Media", CourseInfoModel.MEDIA_TYPE));
                    cInfoModels.add(new CourseInfoModel(null, CourseInfoModel.APPLY_TYPE));

                    String status = response.getString("status");

                    if(status.equals("1")) {
                        JSONObject result = response.getJSONObject("data");
                        myFormatter = new DecimalFormat("0.0");

                        infoModel = new CourseInfoDataModel();

                        infoModel.setWorldRanking(result.getString("worldRanking"));
                        infoModel.setCourseCountry(result.getString("country"));

                        setTitle(result.getString("university"));
                        txtWorldRanking.setText(result.getString("worldRanking"));
                        txtCountry.setText(result.getString("country"));

                        infoModel.setFaculty(result.getString("faculty"));
                        infoModel.setCourseTitle(result.getString("courseTitle"));
                        infoModel.setDurationType(result.getString("durationType"));
                        infoModel.setDurationTime(result.getString("durationTime"));
                       // infoModel.setCostRange(result.getString("costRange"));
                        //infoModel.setCurrency(result.getString("currency"));

                        String getCurrency = result.getString("currency");

                        SharedPreferences getCurrencyId = getSharedPreferences("PREFBASECURRENCY", Context.MODE_PRIVATE);
                        String currComb = getCurrencyId.getString("BASECURRENCY", "");

                        String idCode = currComb.trim()+getCurrency.trim();

                        countData = dbHandler.getCurrencyCount();

                        if(countData > 0) {

                            double ratings = dbHandler.getRatings(idCode);

                            if(ratings > 0) {

                                double costRange = Double.valueOf(result.getString("costRange"));
                                double results = ratings * costRange;

                                String rangeCost = myFormatter.format(results);

                                infoModel.setCostRange(rangeCost);
                                infoModel.setCurrency(currComb.trim());

                            }

                            else {

                                infoModel.setCostRange(result.getString("costRange"));
                                infoModel.setCurrency(getCurrency);

                            }

                        }
                        else {

                            infoModel.setCostRange(result.getString("costRange"));
                            infoModel.setCurrency(getCurrency);

                        }


                        infoModel.setUniCourseWebsite(result.getString("website"));

                        infoModel.setRemarks(result.getString("remarks"));
                        infoModel.setCourseCity(result.getString("city"));
                        infoModel.setCourseID(Integer.valueOf(courseId));

                        JSONObject uniInfo = result.getJSONObject("uniInfo");
                        infoModel.setJobsAvailable(uniInfo.getString("jobsAvailable"));
                        infoModel.setTripAdvisorLink(uniInfo.getString("tripAdLink"));

                        if(result.has("a_levelReq")) {
                            JSONObject aLevelReq = result.getJSONObject("a_levelReq");
                            infoModel.setALvlSubj1(aLevelReq.getString("subj1"));
                            infoModel.setALvlSubj2(aLevelReq.getString("subj2"));
                            infoModel.setALvlSubj3(aLevelReq.getString("subj3"));
                            infoModel.setALvlSubj4(aLevelReq.getString("subj4"));
                            infoModel.setALvlSubj5(aLevelReq.getString("subj5"));
                        }

                        else {
                            infoModel.setALvlSubj1("?");
                            infoModel.setALvlSubj2("?");
                            infoModel.setALvlSubj3("?");
                            infoModel.setALvlSubj4("?");
                            infoModel.setALvlSubj5("?");
                        }

                        if(result.has("o_levelReq")) {
                            JSONObject oLevelReq = result.getJSONObject("o_levelReq");
                            infoModel.setOLvlSubj1(oLevelReq.getString("subj1"));
                            infoModel.setOLvlSubj2(oLevelReq.getString("subj2"));
                            infoModel.setOLvlSubj3(oLevelReq.getString("subj3"));
                            infoModel.setOLvlSubj4(oLevelReq.getString("subj4"));
                            infoModel.setOLvlSubj5(oLevelReq.getString("subj5"));
                        }

                        else {
                            infoModel.setOLvlSubj1("?");
                            infoModel.setOLvlSubj2("?");
                            infoModel.setOLvlSubj3("?");
                            infoModel.setOLvlSubj4("?");
                            infoModel.setOLvlSubj5("?");
                        }


                        JSONObject ieltsRequirements = result.getJSONObject("ieltsReq");
                        infoModel.setIeltsReading(myFormatter.format(Double.valueOf(ieltsRequirements.getString("reading"))));
                        infoModel.setIeltsListening(myFormatter.format(Double.valueOf(ieltsRequirements.getString("listening"))));
                        infoModel.setIeltsWriting(myFormatter.format(Double.valueOf(ieltsRequirements.getString("writing"))));
                        infoModel.setIeltsSpeaking(myFormatter.format(Double.valueOf(ieltsRequirements.getString("speaking"))));
                        infoModel.setIeltsOverall(myFormatter.format(Double.valueOf(ieltsRequirements.getString("overall"))));

                        JSONObject toeflRequirements = result.getJSONObject("tofelReq");
                        infoModel.setToeflReading(myFormatter.format(Double.valueOf(toeflRequirements.getString("reading"))));
                        infoModel.setToeflListening(myFormatter.format(Double.valueOf(toeflRequirements.getString("listening"))));
                        infoModel.setToeflWriting(myFormatter.format(Double.valueOf(toeflRequirements.getString("writing"))));
                        infoModel.setToeflSpeaking(myFormatter.format(Double.valueOf(toeflRequirements.getString("speaking"))));
                        infoModel.setToeflOverall(myFormatter.format(Double.valueOf(toeflRequirements.getString("overall"))));

                        infoModel.setUniName(result.getString("university"));

                        dataInfoModels.add(infoModel);

                        CourseInfoAdapter sAdpt = new CourseInfoAdapter(CourseInfoActivity.this, cInfoModels, dataInfoModels);
                        recyclerViewBody.setAdapter(sAdpt);

                    }

                    else {
                        Toast.makeText(CourseInfoActivity.this, "Could not retrieve course data.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(CourseInfoActivity.this.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
