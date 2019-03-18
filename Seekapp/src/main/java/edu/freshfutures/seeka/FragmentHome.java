package edu.freshfutures.seeka;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import edu.freshfutures.seeka.Models.HomeModel;
import edu.freshfutures.seeka.adapters.HomeAdapter;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentHome extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_TITLE = "section_title";

    ImageButton toolSettings;

    private String title;
    private int page;

    Context ctx;

    RecyclerView recyclerView;
    protected ArrayList<HomeModel> articleList;
    HomeModel hModel;

    private static String TAG = HomeActivity.class.getSimpleName();

    String storedHomeData;

    JSONArray allHomeData;

    public static FragmentHome newInstance(int page, String title) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, page);
        args.putString(ARG_SECTION_TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    public FragmentHome() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        title = getArguments().getString(ARG_SECTION_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar parent = (Toolbar) view.findViewById(R.id.toolbars);
        ((HomeActivity) getActivity()).setSupportActionBar(parent);

        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences getFirstLogged = getActivity().getSharedPreferences("PREFHOMEDATA", Context.MODE_PRIVATE);
        storedHomeData = getFirstLogged.getString("homeData", "[]");

        assert parent != null;
        toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);

        recyclerView = (RecyclerView) view.findViewById(R.id.lisHomeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(storedHomeData.equals("[]")) {
            makeJsonObjectRequest();
        }

        else {
            getHomeData();
        }

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

    private void getHomeData() {

        int likeStatusNum;

        try {
            allHomeData = new JSONArray(storedHomeData);

            for(int i=0; i < allHomeData.length(); i++) {

                hModel = new HomeModel();

                JSONObject dataObj = allHomeData.getJSONObject(i);

                hModel.setCachedData(1);
                hModel.setArticleId(dataObj.getInt("id"));
                hModel.setArticleImage(dataObj.getString("imagepath"));
                hModel.setArticleTitle(dataObj.getString("addType"));
                hModel.setArticleTopic(dataObj.getString("heading"));
                hModel.setArticleDetails(dataObj.getString("content"));
                hModel.setArticleLink(dataObj.getString("url"));

                likeStatusNum = dataObj.getInt("liked");
                hModel.setLikeValue(likeStatusNum);
                hModel.setReviewed(dataObj.getInt("reviewed"));
                hModel.setSharedValue(dataObj.getInt("shared"));


                articleList.add(hModel);

            }

            HomeAdapter adpter = new HomeAdapter(getActivity(), articleList);
            recyclerView.setAdapter(adpter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void makeJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/ArticleService/getActiveArticles";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                JSONArray arrData;
                articleList = new ArrayList<>();

                try {

                    int valueStatus = response.getInt("status");
                    int likeStatusNum;

                    if(valueStatus == 1) {

                        arrData = response.getJSONArray("data");

                        SharedPreferences prefCreditBal = getActivity().getSharedPreferences("PREFHOMEDATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor balEditor = prefCreditBal.edit();
                        balEditor.putString("homeData", arrData.toString());
                        balEditor.apply();

                        for(int i=0; i < arrData.length(); i++) {

                            hModel = new HomeModel();

                            JSONObject dataObj = arrData.getJSONObject(i);

                            hModel.setCachedData(0);
                            hModel.setArticleId(dataObj.getInt("id"));
                            hModel.setArticleImage(dataObj.getString("imagepath"));
                            hModel.setArticleTitle(dataObj.getString("addType"));
                            hModel.setArticleTopic(dataObj.getString("heading"));
                            hModel.setArticleDetails(dataObj.getString("content"));
                            hModel.setArticleLink(dataObj.getString("url"));

                            likeStatusNum = dataObj.getInt("liked");
                            hModel.setLikeValue(likeStatusNum);
                            hModel.setReviewed(dataObj.getInt("reviewed"));
                            hModel.setSharedValue(dataObj.getInt("shared"));


                            articleList.add(hModel);

                        }

                        HomeAdapter adpter = new HomeAdapter(getActivity(), articleList);
                        recyclerView.setAdapter(adpter);

                    }

                    else {
                        Toast.makeText(getActivity(), "Data was not retrieved. Check your internet connection and try again!", Toast.LENGTH_LONG).show();
                    }

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

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}