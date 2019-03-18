package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;

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
import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.freshfutures.seeka.Models.HomeModel;
import edu.freshfutures.seeka.Models.UnlockedModel;
import edu.freshfutures.seeka.adapters.HomeAdapter;
import edu.freshfutures.seeka.adapters.UnlockedRecycleAdapter;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentUnlock extends Fragment  {

    public static ImageButton sortUnlocked;
    public static ImageButton notificationUnlocked;
    public static ImageButton packsUnlocked;
    public static ImageButton produceA4;

    public static TextView txtTabSort;
    public static TextView txtTabNavigation;
    public static TextView txtTabPacks;
    public static TextView txtTabProduce;

    RecyclerView recyclerView;

    protected ArrayList<UnlockedModel> courseList;

    UnlockedModel model;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_TITLE = "section_title";

    protected static String TOKENID = "token_id";

    static boolean set = false;

    private String title;
    private int page;

    private JSONArray allUnlocked;

    private static String TAG = HomeActivity.class.getSimpleName();

    EduQualificationDB dbHandler;
    int countData;

    String cachedUnlockedData;

    DecimalFormat myFormatter;
    Context context;

    ImageButton toolSettings;
    Context ctx;

    public static FragmentUnlock newInstance(int page, String title) {
        FragmentUnlock fragment;
        fragment = new FragmentUnlock();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, page);
        args.putString(ARG_SECTION_TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    public FragmentUnlock() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(ARG_SECTION_NUMBER, 2);
        title = getArguments().getString(ARG_SECTION_TITLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unlock_course, container, false);

        context = getActivity();

        Toolbar parent = (Toolbar) view.findViewById(R.id.toolbars);
        ((HomeActivity) getActivity()).setSupportActionBar(parent);

        ((HomeActivity) context).getSupportActionBar().setDisplayShowTitleEnabled(false);

        assert parent != null;
        toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);

        recyclerView = (RecyclerView) view.findViewById(R.id.lisUnlockedCourses);

        sortUnlocked = (ImageButton) view.findViewById(R.id.imgSortIcon);
        notificationUnlocked = (ImageButton) view.findViewById(R.id.imgNotificationIcon);
        packsUnlocked = (ImageButton) view.findViewById(R.id.imgPacksIcon);
        produceA4 = (ImageButton) view.findViewById(R.id.imgProduceA4Icon);

        txtTabSort = (TextView) view.findViewById(R.id.txtUnlockSort);
        txtTabNavigation = (TextView) view.findViewById(R.id.txtUnlockNotification);
        txtTabPacks = (TextView) view.findViewById(R.id.txtUnlockPacks);
        txtTabProduce = (TextView) view.findViewById(R.id.txtUnlockProduceA4);

        dbHandler = new EduQualificationDB(context);


        if(!set) {
            produceA4.setImageResource(R.mipmap.unlock_producea4_icon);
            txtTabProduce.setTextColor(Color.parseColor("#646969"));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        SharedPreferences getCachedUnlocked = getActivity().getSharedPreferences("PREFUNLOCKEDDATA", Context.MODE_PRIVATE);
        cachedUnlockedData = getCachedUnlocked.getString("unlockedData", "[]");

        if(cachedUnlockedData.equals("[]")) {
            makeJsonObjectRequest();
        }
        else {
            getUnlockedData();
        }


        sortUnlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortUnlocked.setImageResource(R.mipmap.sort_locked_active);
                txtTabSort.setTextColor(Color.parseColor("#0091f0"));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SortDialogFragment newFragments = new SortDialogFragment();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "dialogs");

            }
        });

        notificationUnlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = "dialogsNotice";

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                NotificationFragment noticeFragments = new NotificationFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, noticeFragments, tag)
                        .addToBackStack(null).commit();


            }
        });

        produceA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                produceA4.setImageResource(R.mipmap.unlock_producea4_icon_active);
                txtTabProduce.setTextColor(Color.parseColor("#0091f0"));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                EmailDialogFragment emailFragments = new EmailDialogFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_out);

                transaction.add(android.R.id.content, emailFragments, "dialogsEmail")
                        .addToBackStack(null).commit();

                set = true;

            }
        });

        toolSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SettingsPopup newFragments = new SettingsPopup();

                FragmentTransaction transactions = fragmentManager.beginTransaction();
                newFragments.show(transactions, "settingsDialogs");
            }
        });

        return view;
    }

    private void getUnlockedData() {

        try {
            // JSONArray assetObj = new JSONArray(loadJSONFromAsset());
            allUnlocked = new JSONArray(cachedUnlockedData);

            myFormatter = new DecimalFormat("0.00");

            courseList = new ArrayList<>();

            for(int i=0; i < allUnlocked.length(); i++) {

                model = new UnlockedModel();

                JSONObject dataObj = allUnlocked.getJSONObject(i);

                model.setCached(0);
                model.setCtryImg("http://images.all-free-download.com/images/graphiclarge/paris_france_arc_de_triomphe_218207.jpg");
                model.setRankValue(dataObj.getString("avgWorldRank"));
                model.setDurationTime(dataObj.getString("durationType"));
                model.setDurationType(dataObj.getString("durationTime"));
                model.setCourseTitle(dataObj.getString("courseTitle"));
                model.setCourseId(dataObj.getInt("courseId"));

                String getCurrency = dataObj.getString("currency");

                SharedPreferences getCurrencyId = getActivity().getSharedPreferences("PREFBASECURRENCY", Context.MODE_PRIVATE);
                String currComb = getCurrencyId.getString("BASECURRENCY", "");

                String idCode = currComb.trim()+getCurrency.trim();

                countData = dbHandler.getCurrencyCount();

                if(countData > 0) {

                    double ratings = dbHandler.getRatings(idCode);

                    if(ratings > 0) {

                        double costRange = Double.valueOf(dataObj.getString("costRange"));
                        double results = ratings * costRange;

                        String rangeCost = myFormatter.format(results);

                        model.setCostRange(rangeCost);
                        model.setCurrency(currComb.trim());

                    }

                    else {

                        model.setCostRange(dataObj.getString("costRange"));
                        model.setCurrency(getCurrency);

                    }

                }
                else {

                    model.setCostRange(dataObj.getString("costRange"));
                    model.setCurrency(getCurrency);

                }


                courseList.add(model);

            }

            UnlockedRecycleAdapter adpter = new UnlockedRecycleAdapter(getActivity(), courseList);
            recyclerView.setAdapter(adpter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_UNLOCKED_COURSES;

        JSONObject obj = new JSONObject();

        SharedPreferences getSession = getActivity().getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        try {

            obj.put("sessionToken", session);
            obj.put("pageNo", 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                JSONArray arrData;
                courseList = new ArrayList<>();

                try {
                   // JSONArray assetObj = new JSONArray(loadJSONFromAsset());
                    arrData = response.getJSONArray("data");

                    myFormatter = new DecimalFormat("0.00");

                    SharedPreferences prefCreditBal = getActivity().getSharedPreferences("PREFUNLOCKEDDATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor balEditor = prefCreditBal.edit();
                    balEditor.putString("unlockedData", arrData.toString());
                    balEditor.apply();

                    for(int i=0; i < arrData.length(); i++) {

                        model = new UnlockedModel();

                        JSONObject dataObj = arrData.getJSONObject(i);

                        model.setCached(0);
                        model.setCtryImg("http://images.all-free-download.com/images/graphiclarge/paris_france_arc_de_triomphe_218207.jpg");
                        model.setRankValue(dataObj.getString("avgWorldRank"));
                        model.setDurationTime(dataObj.getString("durationType"));
                        model.setDurationType(dataObj.getString("durationTime"));
                        model.setCourseTitle(dataObj.getString("courseTitle"));
                        model.setCourseId(dataObj.getInt("courseId"));

                        String getCurrency = dataObj.getString("currency");

                        SharedPreferences getCurrencyId = getActivity().getSharedPreferences("PREFBASECURRENCY", Context.MODE_PRIVATE);
                        String currComb = getCurrencyId.getString("BASECURRENCY", "");

                        String idCode = currComb.trim()+getCurrency.trim();

                        countData = dbHandler.getCurrencyCount();

                        if(countData > 0) {

                            double ratings = dbHandler.getRatings(idCode);

                            if(ratings > 0) {

                                double costRange = Double.valueOf(dataObj.getString("costRange"));
                                double results = ratings * costRange;

                                String rangeCost = myFormatter.format(results);

                                model.setCostRange(rangeCost);
                                model.setCurrency(currComb.trim());

                            }

                            else {

                                model.setCostRange(dataObj.getString("costRange"));
                                model.setCurrency(getCurrency);

                            }

                        }
                        else {

                            model.setCostRange(dataObj.getString("costRange"));
                            model.setCurrency(getCurrency);

                        }


                        courseList.add(model);

                    }

                    UnlockedRecycleAdapter adpter = new UnlockedRecycleAdapter(getActivity(), courseList);
                    recyclerView.setAdapter(adpter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("unlockedData/generated.json");
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

    public void changeOnConfirm() {
        sortUnlocked.setImageResource(R.mipmap.up_down);
        txtTabSort.setTextColor(Color.parseColor("#646969"));
    }

    public void changeOnCancel() {
        sortUnlocked.setImageResource(R.mipmap.up_down);
        txtTabSort.setTextColor(Color.parseColor("#696964"));
    }

    public static boolean isEmailDialogOpen() {
        return set;
    }

    public static View isEmailClosedShade(View vc) {

        if(vc.getVisibility() == View.VISIBLE) {
            vc.setVisibility(View.GONE);

        }

        produceA4.setImageResource(R.mipmap.unlock_producea4_icon);
        txtTabProduce.setTextColor(Color.parseColor("#646969"));

        return vc;
    }

}
