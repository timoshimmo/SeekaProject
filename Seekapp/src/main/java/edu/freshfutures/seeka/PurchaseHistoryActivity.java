package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
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

import edu.freshfutures.seeka.Models.LockedModel;
import edu.freshfutures.seeka.Models.PurchaseHistoryModel;
import edu.freshfutures.seeka.Models.TransactionHistoryModel;
import edu.freshfutures.seeka.adapters.LockedAdapter;
import edu.freshfutures.seeka.adapters.PurHistoryAdapter;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseHistoryActivity extends AppCompatActivity {

    private static String TAG = PurchaseHistoryActivity.class.getSimpleName();

    protected static String TOKENID = "token_id";

    PurchaseCreditsFragment purchaseCreditsFragment;

    ImageButton toolbarBack;
    ImageButton toolSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_history_layout);

        Toolbar parent = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarBack = (ImageButton) (parent != null ? parent.findViewById(R.id.imgHomeBack) : null);
        toolSettings = (ImageButton) (parent != null ? parent.findViewById(R.id.imgHomeMenu) : null);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.top_tab);
        tabLayout.addTab(tabLayout.newTab().setText("Purchased Credits"));
        tabLayout.addTab(tabLayout.newTab().setText("Used Credits"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.purchase_pager);
        final PurHistoryAdapter adapter = new PurHistoryAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        if (viewPager != null) {
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        purchaseCreditsFragment = new PurchaseCreditsFragment();

        makeJsonObjectRequest();

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

    }

    private void makeJsonObjectRequest() {


        String requestURL = Url.URL_PURCHASE_HISTORY;

        JSONObject obj = new JSONObject();

        SharedPreferences getSession = getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
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

                purchaseCreditsFragment.getResponse(response.toString(), PurchaseHistoryActivity.this);



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
