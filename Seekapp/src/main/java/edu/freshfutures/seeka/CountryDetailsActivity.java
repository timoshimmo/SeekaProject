package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.view.ViewGroup;
import android.view.animation.Animation;

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
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.adapters.CountryMediaAdapter;
import edu.freshfutures.seeka.interfaces.ToolBarBack;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

public class CountryDetailsActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener, ToolBarBack {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;

    int position;

    TextView txtVisa;
    TextView txtCostLiving;
    TextView txtClimate;
    TextView txtJobProspect;
    TextView txtIntlHealth;
    TextView txtEmergency;
    TextView txtMedia;

    TextView txtVisaDetails;
    TextView txtCostLivingDetails;
    TextView txtClimateDetails;
    TextView txtJobProspectDetails;
    TextView txtIntlHealthDetails;
    TextView txtEmergencyDetails;
    TextView txtMediaDetails;

    ImageView arrowVisa;
    ImageView arrowCostLiving;
    ImageView arrowClimate;
    ImageView arrowJobProspect;
    ImageView arrowIntlHealth;
    ImageView arrowEmergency;
    ImageView arrowMedia;

    private String tags;

    Context ctx;

    String ctryName;
    String ctryCode;

    Animation swing_down;
    Animation swing_up;

    ImageView ctryImage;

    LinearLayout btnVisa;
    LinearLayout btnCostLiving;
    LinearLayout btnClimate;
    LinearLayout btnJobProspect;
    LinearLayout btnHealthCover;
    LinearLayout btnEmergency;
    LinearLayout btnMedia;

    LinearLayout VisaValueView;
    LinearLayout CostLivingValueView;
    LinearLayout ClimateValueView;
    LinearLayout JobProspectValueView;
    LinearLayout HealthCoverValueView;
    LinearLayout EmergencyValueView;
    LinearLayout MediaValueView;

    protected static String TOKENID = "token_id";

    public static ArrayList<String> moreData;
    public static int ctryBackImage;

    private static String TAG = CountryDetailsActivity.class.getSimpleName();

    private CtryAboutDialogFragment aboutFragment;

    String[] videoIds;
    String[] vidViews;
    String[] vidTitle;
    String[] vidPublished;
    String[] vidDuration;

    protected YouTubeThumbnailView tubeThumbnailView;
    protected TextView txtDuration;
    protected TextView txtLastUpload;
    protected TextView txtViews;
    protected TextView txtTitle;
    protected ImageView noVid;

    LinearLayout youtubeRow;

    private static RecyclerView mYoutubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        View v = customBar.getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);

        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);
        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);
        toolSettings = (ImageButton) customView.findViewById(R.id.imgHomeMenu);
        actionBarTitle = (TextView) customView.findViewById(R.id.txtHomeTitle);
        ctryImage = (ImageView) findViewById(R.id.imgCountryInfo);

        LinearLayout btnAbout = (LinearLayout) findViewById(R.id.btnAboutCountry);

        btnVisa = (LinearLayout) findViewById(R.id.btnVisaView);
        btnCostLiving = (LinearLayout) findViewById(R.id.btnCostView);
        btnClimate = (LinearLayout) findViewById(R.id.btnClimateView);
        btnJobProspect = (LinearLayout) findViewById(R.id.btnJobView);
        btnHealthCover = (LinearLayout) findViewById(R.id.btnHealthCoverView);
        btnEmergency = (LinearLayout) findViewById(R.id.btnEmergencyView);
        btnMedia = (LinearLayout) findViewById(R.id.btnMediaView);

        VisaValueView = (LinearLayout) findViewById(R.id.visa_value);
        CostLivingValueView = (LinearLayout) findViewById(R.id.cost_living_value_value);
        ClimateValueView = (LinearLayout) findViewById(R.id.climate_value);
        JobProspectValueView = (LinearLayout) findViewById(R.id.job_prospect_value);
        HealthCoverValueView = (LinearLayout) findViewById(R.id.international_health_cover_value);
        EmergencyValueView = (LinearLayout) findViewById(R.id.emergency_contact_value);
        MediaValueView = (LinearLayout) findViewById(R.id.media_value);

        txtVisa = (TextView) findViewById(R.id.txtAboutCtryVisa);
        txtCostLiving = (TextView) findViewById(R.id.txtAboutCtryCLiving);
        txtClimate = (TextView) findViewById(R.id.txtAboutCtryClimate);
        txtJobProspect = (TextView) findViewById(R.id.txtAboutCtryJobs);
        txtIntlHealth = (TextView) findViewById(R.id.txtAboutCtryHealth);
        txtEmergency = (TextView) findViewById(R.id.txtAboutCtryEmergency);
        txtMedia = (TextView) findViewById(R.id.txtAboutCtryMedia);

        txtVisaDetails = (TextView) findViewById(R.id.txtCountryInfoDetails);
        txtCostLivingDetails = (TextView) findViewById(R.id.txtCostDetails);
        txtClimateDetails = (TextView) findViewById(R.id.txtCtryClimateDetails);
        txtJobProspectDetails = (TextView) findViewById(R.id.txtJobProspectDetails);
        txtIntlHealthDetails = (TextView) findViewById(R.id.txtIntlHealthCoverDetails);
        txtEmergencyDetails = (TextView) findViewById(R.id.txtEmergencyContactDetails);
        txtMediaDetails = (TextView) findViewById(R.id.txtMediaDetails);

        arrowVisa = (ImageView) findViewById(R.id.imgVisaIndicator);
        arrowCostLiving = (ImageView) findViewById(R.id.imgCostIndicator);
        arrowClimate = (ImageView) findViewById(R.id.imgClimateIndicator);
        arrowJobProspect = (ImageView) findViewById(R.id.imgJobIndicator);
        arrowIntlHealth = (ImageView) findViewById(R.id.imgHealthIndicator);
        arrowEmergency = (ImageView) findViewById(R.id.imgEmergencyIndicator);
        arrowMedia = (ImageView) findViewById(R.id.imgMediaIndicator);

        tubeThumbnailView = (YouTubeThumbnailView) findViewById(R.id.imgYoutubeThumb);
        txtDuration = (TextView) findViewById(R.id.txtYouTubeDuration);
        txtLastUpload = (TextView) findViewById(R.id.txtYouTubeLastUploaded);
        txtViews = (TextView) findViewById(R.id.txtYouTubeViews);
        txtTitle = (TextView) findViewById(R.id.txtYouTubeTitle);
        noVid = (ImageView) findViewById(R.id.imgNoVideo);

        youtubeRow = (LinearLayout) findViewById(R.id.youtube_row);

        moreData = new ArrayList<>();

        final int[] CountryBackground = new int[]{R.mipmap.search_australia, R.mipmap.search_austria, R.mipmap.search_brazil,
                R.mipmap.search_canada, R.mipmap.search_china, R.mipmap.search_czech_republic, R.mipmap.search_denmark,
                R.mipmap.search_finland, R.mipmap.search_france, R.mipmap.search_germany, R.mipmap.search_iceland,
                R.mipmap.search_india, R.mipmap.search_ireland, R.mipmap.search_italy, R.mipmap.search_japan,
                R.mipmap.search_malaysia, R.mipmap.search_mexico, R.mipmap.search_netherlands,
                R.mipmap.search_new_zealand, R.mipmap.search_poland, R.mipmap.search_russia,
                R.mipmap.search_singapore, R.mipmap.search_slovenia, R.mipmap.search_south_africa,
                R.mipmap.search_spain, R.mipmap.search_sweden, R.mipmap.search_switzerland,
                R.mipmap.search_u_a_e, R.mipmap.search_u_s_a, R.mipmap.search_united_kingdom};

        int[] CountryCodes = new int[]{R.string.ctry_code_australia, R.string.ctry_code_austria, R.string.ctry_code_brazil,
                R.string.ctry_code_canada, R.string.ctry_code_china, R.string.ctry_code_czech, R.string.ctry_code_denmark,
                R.string.ctry_code_finland, R.string.ctry_code_france, R.string.ctry_code_germany, R.string.ctry_code_iceland,
                R.string.ctry_code_india, R.string.ctry_code_ireland, R.string.ctry_code_italy, R.string.ctry_code_japan,
                R.string.ctry_code_malaysia, R.string.ctry_code_mexico, R.string.ctry_code_netherlands, R.string.ctry_code_new_zealand,
                R.string.ctry_code_poland, R.string.ctry_code_russia, R.string.ctry_code_singapore,
                R.string.ctry_code_slovenia, R.string.ctry_code_south_africa, R.string.ctry_code_spain,
                R.string.ctry_code_sweden, R.string.ctry_code_switzerland, R.string.ctry_code_u_a_e, R.string.ctry_code_u_s_a,
                R.string.ctry_code_uk};

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            position = extras.getInt("POSITION");
            ctryName = extras.getString("COUNTRYNAME");

            if (ctryImage != null) {
                //getResources().getString(countryInfo.get(CountryInfoActivity.COUNTRY_NAME)[position])
                ctryBackImage = CountryBackground[position];
                ctryImage.setImageResource(ctryBackImage);
            }

            ctryCode = getResources().getString(CountryCodes[position]);
            setTitle(ctryName);

        }

        if (isNetworkAvailable()) {
            makeJsonObjectRequest();
        } else {
            Toast.makeText(getApplicationContext(), "Internet network is not available. Connect and try again!", Toast.LENGTH_LONG).show();

        }

        initMedia();

        btnVisa.setOnClickListener(this);
        btnCostLiving.setOnClickListener(this);
        btnClimate.setOnClickListener(this);
        btnJobProspect.setOnClickListener(this);
        btnHealthCover.setOnClickListener(this);
        btnEmergency.setOnClickListener(this);
        btnMedia.setOnClickListener(this);

        if (btnAbout != null) {
            btnAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int vs = View.VISIBLE;
                    int ivs = View.INVISIBLE;

                    ToolBarBack toolBarCallBack = (ToolBarBack) CountryDetailsActivity.this;

                    aboutFragment = CtryAboutDialogFragment.newInstance();

                            String tag = "countryAbout";

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    aboutFragment = new CtryAboutDialogFragment();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.add(android.R.id.content, aboutFragment, tag)
                            .addToBackStack(null).commit();

                    toolBarCallBack.stringTag(tag);
                    toolBarCallBack.switchBackVisible(vs, ivs);
                }
            });
        }


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

        dialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                switchBackInvisible(vs, ivs);

                String fragTag = getStringTag();
                DialogFragment dialogFragment = (DialogFragment) getSupportFragmentManager().findFragmentByTag(fragTag);

                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }

                setTitle(ctryName);

            }
        });


    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_COUNTRY_INFO;

        JSONObject obj = new JSONObject();
        SharedPreferences sharedPref = CountryDetailsActivity.this.getSharedPreferences(
                getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);

        String sId = sharedPref.getString(TOKENID, "");

        try {
            obj.put("countryCode", ctryCode);
            obj.put("seddionToken", sId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                  Log.d(TAG, response.toString());

                try {

                    JSONObject objData = response.getJSONObject("data");

                    txtVisaDetails.setText(objData.getString("visa"));
                    txtCostLivingDetails.setText(objData.getString("costOfLiving"));
                    txtClimateDetails.setText(objData.getString("climate"));
                    txtJobProspectDetails.setText(objData.getString("jobPorspect"));
                    txtIntlHealthDetails.setText(objData.getString("internationalHealthCover"));
                    txtEmergencyDetails.setText(objData.getString("emgHelp"));
                    txtMediaDetails.setText(objData.getString("countryFacts"));

                    moreData.add(objData.getString("area"));
                    moreData.add(objData.getString("population"));
                    moreData.add(objData.getString("capitalCity"));
                    moreData.add(objData.getString("people"));
                    moreData.add(objData.getString("language"));

                    moreData.add(objData.getString("religion"));
                    moreData.add(objData.getString("headOfGovernment"));
                    moreData.add(objData.getString("gdp"));
                    moreData.add(objData.getString("gdpPerCapita"));
                    moreData.add(objData.getString("annualGrowth"));
                    moreData.add(objData.getString("inflation"));
                    moreData.add(objData.getString("majorIndustries"));
                    moreData.add(objData.getString("majorTradingPartners"));


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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public void initMedia() {

        CountryMediaAdapter mAdapter;

        videoIds = new String[]{"7uY0Ab5HlZ0"};
        vidTitle = new String[]{"Toronto Vacation Travel Guide | Expedia"};
        vidViews = new String[]{"481,650 views"};
        vidPublished = new String[]{"3 years ago"};
        vidDuration = new String[]{"4:33"};

        mYoutubeView = (RecyclerView) findViewById(R.id.ctryMedia);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CountryDetailsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mYoutubeView.setLayoutManager(linearLayoutManager);

        mYoutubeView.setHasFixedSize(true);

        mYoutubeView.setItemAnimator(new DefaultItemAnimator());
        mAdapter =new CountryMediaAdapter(CountryDetailsActivity.this, videoIds, vidTitle, vidPublished, vidViews, vidDuration);
        mYoutubeView.setAdapter(mAdapter);

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void switchBackVisible(int vs, int ivs) {

        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(toolbarBack.getVisibility() == vs) {
            toolbarBack.setVisibility(ivs);
            dialogBack.setVisibility(vs);
        }
        else {
            dialogBack.setVisibility(vs);
        }

    }

    @Override
    public void switchBackInvisible(int vs, int ivs) {

        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(dialogBack.getVisibility() == vs) {
            dialogBack.setVisibility(ivs);

            if(toolbarBack.getVisibility() == ivs) {
                toolbarBack.setVisibility(vs);
            }

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
    public void onClick(View v) {

        arrowVisa = (ImageView) findViewById(R.id.imgVisaIndicator);
        arrowCostLiving = (ImageView) findViewById(R.id.imgCostIndicator);
        arrowClimate = (ImageView) findViewById(R.id.imgClimateIndicator);
        arrowJobProspect = (ImageView) findViewById(R.id.imgJobIndicator);
        arrowIntlHealth = (ImageView) findViewById(R.id.imgHealthIndicator);
        arrowEmergency = (ImageView) findViewById(R.id.imgEmergencyIndicator);
        arrowMedia = (ImageView) findViewById(R.id.imgMediaIndicator);

        if(v == btnVisa) {

            if (VisaValueView.getVisibility() == View.GONE) {

                VisaValueView.setVisibility(View.VISIBLE);
              //  VisaValueView.startAnimation(swing_down);

                txtVisa.setTextColor(Color.parseColor("#0091f0"));
                if(Build.VERSION.SDK_INT > 21) {
                    arrowVisa.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowVisa.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }

            } else {

                VisaValueView.setVisibility(View.GONE);
                txtVisa.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowVisa.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowVisa.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
           //     VisaValueView.startAnimation(swing_up);

            }

        }

        if(v == btnCostLiving) {

            if (CostLivingValueView.getVisibility() == View.GONE) {

                CostLivingValueView.setVisibility(View.VISIBLE);
            //    CostLivingValueView.startAnimation(swing_down);

                txtCostLiving.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowCostLiving.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowCostLiving.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }


            } else {

                CostLivingValueView.setVisibility(View.GONE);

                txtCostLiving.setTextColor(Color.parseColor("#646969"));
                if(Build.VERSION.SDK_INT > 21) {
                    arrowCostLiving.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowCostLiving.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
            //    CostLivingValueView.startAnimation(swing_up);

            }
        }

        if(v == btnClimate) {

            if (ClimateValueView.getVisibility() == View.GONE) {

                ClimateValueView.setVisibility(View.VISIBLE);
               // ClimateValueView.startAnimation(swing_down);

                txtClimate.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowClimate.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowClimate.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }

            } else {

                ClimateValueView.setVisibility(View.GONE);

                txtClimate.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowClimate.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowClimate.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
             //   ClimateValueView.startAnimation(swing_up);

            }
        }

        if(v == btnJobProspect) {

            if (JobProspectValueView.getVisibility() == View.GONE) {

                JobProspectValueView.setVisibility(View.VISIBLE);
              //  JobProspectValueView.startAnimation(swing_down);

                txtJobProspect.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowJobProspect.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowJobProspect.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }


            } else {

                JobProspectValueView.setVisibility(View.GONE);

                txtJobProspect.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowJobProspect.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowJobProspect.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
              //  JobProspectValueView.startAnimation(swing_up);

            }
        }

        if(v == btnHealthCover) {

            if (HealthCoverValueView.getVisibility() == View.GONE) {

                HealthCoverValueView.setVisibility(View.VISIBLE);
            //    HealthCoverValueView.startAnimation(swing_down);

                txtIntlHealth.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowIntlHealth.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowIntlHealth.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }

            } else {

                HealthCoverValueView.setVisibility(View.GONE);

                txtIntlHealth.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowIntlHealth.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowIntlHealth.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
            //    HealthCoverValueView.startAnimation(swing_up);

            }

        }

        if(v == btnEmergency) {

            if (EmergencyValueView.getVisibility() == View.GONE) {

                EmergencyValueView.setVisibility(View.VISIBLE);
            //    EmergencyValueView.startAnimation(swing_down);

                txtEmergency.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowEmergency.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowEmergency.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }

            } else {

                EmergencyValueView.setVisibility(View.GONE);

                txtEmergency.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowEmergency.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowEmergency.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }
              //  EmergencyValueView.startAnimation(swing_up);

            }

        }

        if(v == btnMedia) {

            if (MediaValueView.getVisibility() == View.GONE) {

                MediaValueView.setVisibility(View.VISIBLE);
                txtMedia.setTextColor(Color.parseColor("#0091f0"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowMedia.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow, null));
                }
                else {
                    arrowMedia.setImageDrawable(getResources().getDrawable(R.mipmap.filter_up_arrow));
                }

            } else {

                MediaValueView.setVisibility(View.GONE);
                txtMedia.setTextColor(Color.parseColor("#646969"));

                if(Build.VERSION.SDK_INT > 21) {
                    arrowMedia.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow, null));
                }
                else {
                    arrowMedia.setImageDrawable(getResources().getDrawable(R.mipmap.filter_down_arrow));
                }

            }
        }

    }
}
