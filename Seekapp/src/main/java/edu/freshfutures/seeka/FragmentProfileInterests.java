package edu.freshfutures.seeka;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.freshfutures.seeka.volley.custom.application.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfileInterests extends Fragment {

    public static ImageButton btnAddInterests;
    public static ImageButton btnAddCountries;
    public static ImageButton btnAddCareer;

    public static Button btnUpdateInterest;


    public static RecyclerView hobbyRecyclerView;
    public static RecyclerView ctryRecyclerView;
    public static RecyclerView careerRecyclerView;

    protected static String TOKENID = "token_id";
    public FragmentProfileInterests() {
        //Required empty public constructor...
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_interests, container, false);

        btnAddInterests = (ImageButton) view.findViewById(R.id.btnProfAddHobInterest);
        btnAddCountries = (ImageButton) view.findViewById(R.id.btnProfAddCountries);
        btnAddCareer = (ImageButton) view.findViewById(R.id.btnProfAddCareers);

        btnAddCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProfileCtryInterest.class);
                startActivity(intent);
            }
        });

        btnAddInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProfileInterestListActivity.class);
                startActivity(intent);


            }
        });

        btnAddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProfileCareerActivity.class);
                startActivity(intent);

            }
        });

        btnUpdateInterest = (Button) view.findViewById(R.id.btnUpdateEdu);

        btnUpdateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeJsonObjectRequest();

            }
        });


        hobbyRecyclerView = (RecyclerView) view.findViewById(R.id.lisProfHobbiesList);
        hobbyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hobbyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ctryRecyclerView = (RecyclerView) view.findViewById(R.id.lisProfCountriesList);
        ctryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ctryRecyclerView.setItemAnimator(new DefaultItemAnimator());

        careerRecyclerView = (RecyclerView) view.findViewById(R.id.lisProfCareerList);
        careerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        careerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void makeJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UserService/userInterests/";
        //String requestURL = Url.URL_UPDATE_PROFILE_INFO;

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);

        String sId = sharedPref.getString(TOKENID, "");

        JSONObject obj = new JSONObject();

        try {

            SharedPreferences getHobbies = getActivity().getSharedPreferences("HOBBIESPREF", Context.MODE_PRIVATE);
            JSONArray hobbyArray = new JSONArray(getHobbies.getString("HOBBYLIST", "[]"));

            SharedPreferences getCtryInterest = getActivity().getSharedPreferences("CTRYINTERESTPREF", Context.MODE_PRIVATE);
            JSONArray ctryListArray = new JSONArray(getCtryInterest.getString("CTRYINTERESTLIST", "[]"));

            SharedPreferences getCareer = getActivity().getSharedPreferences("CAREERPREF", Context.MODE_PRIVATE);
            JSONArray careerArray = new JSONArray(getCareer.getString("CAREERLIST", "[]"));

            obj.put("careerIntrests", careerArray);
            obj.put("countryIntrests", ctryListArray);
            obj.put("hobbies", hobbyArray);
            obj.put("jobRole", "jobs");
            obj.put("sessionId", sId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String message = response.getString("message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


}
