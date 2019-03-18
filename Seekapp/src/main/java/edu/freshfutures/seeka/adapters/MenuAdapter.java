package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.CompareCoursesActivity;
import edu.freshfutures.seeka.CountryInfoActivity;
import edu.freshfutures.seeka.FragmentCurrencyConverter;
import edu.freshfutures.seeka.FragmentPrivatePolicy;
import edu.freshfutures.seeka.FragmentTerms;
import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.LoginActivity;
import edu.freshfutures.seeka.Models.MenuModel;
import edu.freshfutures.seeka.NotifictionSettingsFragment;
import edu.freshfutures.seeka.ProfileActivity;
import edu.freshfutures.seeka.PurchaseCreditsActivity;
import edu.freshfutures.seeka.PurchaseHistoryActivity;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by freshfuturesmy on 20/10/16.
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MenuModel> mMenusData;
    private Context mContext;

    private static final int SECTION_TYPE = 0;
    private static final int ROW_TYPE = 1;
    private static final int NOTIFICATION_TYPE = 2;
    private static final int EMPTY_SECTION_TYPE = 3;

    protected static String TOKENID = "token_id";
    private static String TAG = HomeActivity.class.getSimpleName();

    public MenuAdapter(Context ctx, ArrayList<MenuModel> md) {
        this.mMenusData = md;
        this.mContext = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();

        switch(viewType) {

            case SECTION_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.list_item_section, parent, false);
                return new MenuAdapter.SectionHolder(view);

            case ROW_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.list_settings_structure, parent, false);
                return new MenuAdapter.MenuRowHolder(view);

            case NOTIFICATION_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.list_notification_structure, parent, false);
                return new MenuAdapter.NotificationHolder(view);

            case EMPTY_SECTION_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.list_item_section_empty, parent, false);
                return new MenuAdapter.EmptySectionHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MenuModel mModel = mMenusData.get(position);

        if(mModel != null) {

            switch(mModel.getRowType()) {

                case SECTION_TYPE:

                    ((SectionHolder)holder).tvTitle.setText(mModel.getMenuTitle());

                    break;

                case ROW_TYPE:

                    ((MenuRowHolder)holder).tvMenuTitle.setText(mModel.getMenuTitle());
                    ((MenuRowHolder)holder).menuIcon.setImageResource(mModel.getMenuIcon());

                    final String titleValue = ((MenuRowHolder)holder).tvMenuTitle.getText().toString();

                    ((MenuRowHolder)holder).btnRowClicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (titleValue.equalsIgnoreCase("Profile")) {
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                mContext.startActivity(intent);
                            }

                            if(titleValue.equalsIgnoreCase("Compare Courses")) {

                                Intent intent = new Intent(mContext, CompareCoursesActivity.class);
                                mContext.startActivity(intent);

                            }

                            if(titleValue.equalsIgnoreCase("Country Information")) {

                                Intent intent = new Intent(mContext, CountryInfoActivity.class);
                                mContext.startActivity(intent);

                            }

                            if(titleValue.equalsIgnoreCase("Notification Settings")) {


                                String tag = "dialogsNotificationSettings";
                                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                                NotifictionSettingsFragment notifictionSettingsFragment = new NotifictionSettingsFragment();

                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                                transaction.add(android.R.id.content, notifictionSettingsFragment, tag)
                                        .addToBackStack(null).commit();

                            }

                            if(titleValue.equalsIgnoreCase("Terms and Conditions")) {

                                String tag = "dialogsTerms";
                                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                                FragmentTerms termsFragment = new FragmentTerms();

                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                                transaction.add(android.R.id.content, termsFragment, tag)
                                        .addToBackStack(null).commit();

                            }

                            if(titleValue.equalsIgnoreCase("Privacy & Policy")) {

                                String tag = "dialogsPolicy";
                                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                                FragmentPrivatePolicy policyFragment = new FragmentPrivatePolicy();

                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                                transaction.add(android.R.id.content, policyFragment, tag)
                                        .addToBackStack(null).commit();

                            }

                            if(titleValue.equalsIgnoreCase("Currency Converter")) {

                                String tag = "dialogsConverter";
                                FragmentManager fragmentManager = ((HomeActivity)mContext).getSupportFragmentManager();
                                FragmentCurrencyConverter currencyFragment = new FragmentCurrencyConverter();

                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                                transaction.add(android.R.id.content, currencyFragment, tag)
                                        .addToBackStack(null).commit();

                            }

                            if(titleValue.equalsIgnoreCase("Purchase History")) {

                                Intent intent = new Intent(mContext, PurchaseHistoryActivity.class);
                                mContext.startActivity(intent);

                            }

                            if(titleValue.equalsIgnoreCase("Logout")) {

                                if (isNetworkAvailable()) {
                                    makeJsonObjectRequest();

                                    SharedPreferences prefCreditBal = mContext.getSharedPreferences("PREFHOMEDATA", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor balEditor = prefCreditBal.edit();
                                    balEditor.putString("homeData", "[]");
                                    balEditor.apply();

                                    SharedPreferences prefunlockedData = mContext.getSharedPreferences("PREFUNLOCKEDDATA", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor unlockedEditor = prefunlockedData.edit();
                                    unlockedEditor.putString("unlockedData", "[]");
                                    unlockedEditor.apply();

                                } else {
                                    Toast.makeText(mContext, "Internet network is not available. Connect and try again!", Toast.LENGTH_LONG).show();

                                }

                            }

                        }
                    });

                    break;

                case NOTIFICATION_TYPE:

                    ((NotificationHolder)holder).tvMenuTitle.setText(mModel.getMenuTitle());
                    ((NotificationHolder)holder).menuIcon.setImageResource(mModel.getMenuIcon());
                    ((NotificationHolder)holder).tvNotificationValue.setText(mModel.getMenuNotifications());

                    final String titleString = ((NotificationHolder)holder).tvMenuTitle.getText().toString();

                    if(titleString.equals("My Credits")) {

                        ((NotificationHolder)holder).tvNotificationValue.setTextColor(Color.parseColor("#00aff0"));

                    }

                    if(titleString.equals("Notifications")) {

                        ((NotificationHolder)holder).tvNotificationValue.setPadding((int) mContext.getResources().getDimension(R.dimen.menu_value_padding_left),
                                (int) mContext.getResources().getDimension(R.dimen.menu_title_padding_top),
                                (int) mContext.getResources().getDimension(R.dimen.menu_value_padding_left),
                                (int) mContext.getResources().getDimension(R.dimen.menu_title_padding_top));

                        if(Build.VERSION.SDK_INT > 21) {
                            ((NotificationHolder)holder).tvNotificationValue.setBackground(mContext.getResources().getDrawable(R.drawable.menu_figures_background, null));
                        }
                        else {
                            ((NotificationHolder)holder).tvNotificationValue.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.menu_figures_background));
                        }

                    }

                    ((NotificationHolder)holder).btnNotificationClicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(titleString.equalsIgnoreCase("My Credits")) {

                                Intent intent = new Intent(mContext, PurchaseCreditsActivity.class);
                                mContext.startActivity(intent);

                            }


                        }
                    });

                    break;

                case EMPTY_SECTION_TYPE:

                    break;

            }

        }

    }


    @Override
    public int getItemCount() {
        return mMenusData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMenusData != null) {
            MenuModel object = mMenusData.get(position);
            if (object != null) {
                return object.getRowType();
            }
        }
        return -1;
    }

    private static class SectionHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        SectionHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.list_section_view);

        }

    }

    private static class MenuRowHolder extends RecyclerView.ViewHolder {

        ImageView menuIcon;
        TextView tvMenuTitle;
        RelativeLayout btnRowClicked;

        MenuRowHolder(View itemView) {

            super(itemView);

            menuIcon = (ImageView) itemView.findViewById(R.id.img_settings_title);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.text_settings_value);
            btnRowClicked = (RelativeLayout) itemView.findViewById(R.id.menu_list_back);

        }

    }

    private static class NotificationHolder extends RecyclerView.ViewHolder {

        ImageView menuIcon;
        TextView tvMenuTitle;
        TextView tvNotificationValue;
        RelativeLayout btnNotificationClicked;

        NotificationHolder(View itemView) {
            super(itemView);

            menuIcon = (ImageView) itemView.findViewById(R.id.menu_icon);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.text_menu_title);
            tvNotificationValue = (TextView) itemView.findViewById(R.id.text_menu_notify);
            btnNotificationClicked = (RelativeLayout) itemView.findViewById(R.id.menu_list_back);

        }

    }

    private static class EmptySectionHolder extends RecyclerView.ViewHolder {

        EmptySectionHolder(View itemView) {
            super(itemView);

        }

    }

    private void makeJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UserService/logout";
        JSONObject obj = new JSONObject();

        SharedPreferences getSession = mContext.getSharedPreferences(mContext.getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        try {
            obj.put("sessionToken", session);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                String statusValue;

                try {

                    statusValue = response.getString("status");

                    if(statusValue.equals("1")) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this will clear all the stack
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                        ((HomeActivity)mContext).finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(mContext.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
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


}
