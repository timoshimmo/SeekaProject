package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
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

import edu.freshfutures.seeka.Models.NotificationModel;
import edu.freshfutures.seeka.adapters.NotificationAdapter;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by tokmang on 4/19/2016.
 */
public class NotificationFragment extends DialogFragment {

    RecyclerView lisNotification;
    Context ctx;

    ImageButton btnCancelNotifi;
    ImageButton btnConfirmNotifi;

    public String[] notificationTitles;
    Dialog d;
    private static String TAG = CountryDetailsActivity.class.getSimpleName();

    NotificationAdapter adp;

    NotificationModel model;
    ArrayList<NotificationModel> arrNotifyModel;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unlocked_notification_layout, container, false);

        ctx = getActivity();

        btnCancelNotifi = (ImageButton) view.findViewById(R.id.btnNotifiCancel);
        btnConfirmNotifi = (ImageButton) view.findViewById(R.id.btnNotifiConfirm);
        notificationTitles = getResources().getStringArray(R.array.notification_settings_array);

        lisNotification = (RecyclerView) view.findViewById(R.id.lisNotification);
        lisNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        lisNotification.setHasFixedSize(true);
        lisNotification.setItemAnimator(new DefaultItemAnimator());

        getJsonObjectRequest();

        //RecyclerView.ItemDecoration itemDecoration = new
        //         ListDividerItemDecoration(getActivity(), ListDividerItemDecoration.VERTICAL_LIST);
        //  recyclerView.addItemDecoration(itemDecoration);

        adp = new NotificationAdapter(ctx, arrNotifyModel, notificationTitles);
        lisNotification.setAdapter(adp);

        btnCancelNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        btnConfirmNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adp.setNotifyOptions();

                SharedPreferences getBalance = getActivity().getSharedPreferences("PREFNOTIFISETTINGS", Context.MODE_PRIVATE);
                String notificationSettings = getBalance.getString("notifiSettings", "{}");

                Toast.makeText(getActivity(), notificationSettings, Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsNotice");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

     private void getJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UserService/getUserNotificationSettings/";
        JSONObject obj = new JSONObject();

        try {
            obj.put("sessionToken", "d2a757a6aa875a5a46491bce5a522895");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    String status = response.getString("status");
                    JSONObject object;
                    JSONArray notifiSettings;

                    if(status.equals("1")) {

                        object = response.getJSONObject("data");

                        arrNotifyModel = new ArrayList<>();

                        if(object.getJSONArray("userNotificationSettingList").length() > 0) {

                            notifiSettings = object.getJSONArray("userNotificationSettingList");


                            for(int i=0; i < notificationTitles.length; i++) {

                                model = new NotificationModel();

                                if(i > notifiSettings.length()) {
                                    model.setNotifiPosition(0);
                                    model.setEmailValue(0);
                                    model.setMobileValue(0);
                                }
                                else {
                                    JSONObject notifiData = notifiSettings.getJSONObject(i);

                                    model.setNotifiPosition(notifiData.getInt("notificationId"));
                                    model.setEmailValue(notifiData.getInt("email"));
                                    model.setMobileValue(notifiData.getInt("popup"));
                                }

                                arrNotifyModel.add(model);

                            }

                        }

                        else {
                            for(int i=0; i < notificationTitles.length; i++) {

                                model = new NotificationModel();

                                model.setEmailValue(0);
                                model.setMobileValue(0);

                                arrNotifyModel.add(model);

                            }
                        }


                    }

                    else {

                        String msg = response.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
