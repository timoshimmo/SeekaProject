package edu.freshfutures.seeka;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.MenuModel;
import edu.freshfutures.seeka.adapters.MenuAdapter;
import edu.freshfutures.seeka.volley.custom.application.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenu extends Fragment  {

    RecyclerView recyclerView;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_TITLE = "section_title";

    private String title;
    private int page;

    ImageButton toolSettings;

    String creditBalance;

    protected ArrayList<MenuModel> menuList;
    protected MenuModel mModel;

    Context ctx;

    public FragmentMenu() {
        // Required empty public constructor
    }

    public static FragmentMenu newInstance(int page, String title) {
        FragmentMenu fragment = new FragmentMenu();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, page);
        args.putString(ARG_SECTION_TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(ARG_SECTION_NUMBER, 3);
        title = getArguments().getString(ARG_SECTION_TITLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Toolbar parent = (Toolbar) view.findViewById(R.id.toolbars);
        ((HomeActivity) getActivity()).setSupportActionBar(parent);

        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        assert parent != null;
        toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_settings_views);

        SharedPreferences getBalance = getActivity().getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
        creditBalance = getBalance.getString("creditBalance", "");


     /*   items.add(new SectionItem("Profile"));
        items.add(new EntryItem(R.mipmap.menu_profile, "Profile", ""));
        items.add(new EntryItem(R.mipmap.menu_notifications, "Notifications", "60"));

        items.add(new SectionItem("Advisor"));
        items.add(new EntryItem(R.mipmap.menu_compare_courses, "Compare Courses", ""));
        items.add(new EntryItem(R.mipmap.menu_country_information, "Country Information", ""));
        items.add(new EntryItem(R.mipmap.menu_need_help, "Need Help", ""));

        items.add(new SectionItem("Purchases"));
        items.add(new EntryItem(R.mipmap.menu_my_credits, "My Credits", creditBalance));
        items.add(new EntryItem(R.mipmap.menu_purchase_history, "Purchase History", ""));

        items.add(new SectionItem("Settings"));
        items.add(new EntryItem(R.mipmap.menu_notifications_settings, "Notification Settings", ""));
        items.add(new EntryItem(R.mipmap.menu_terms_and_conditions, "Terms and Conditions", ""));
        items.add(new EntryItem(R.mipmap.menu_privacy_and_policy, "Privacy & Policy", ""));
        items.add(new EntryItem(R.mipmap.menu_currency_converter, "Currency Converter", ""));

        items.add(new SectionItem(""));
        items.add(new EntryItem(R.mipmap.menu_logout, "Logout", ""));
        items.add(new SectionItem(""));

        EntryAdapter adapter = new EntryAdapter(getActivity(), items); */

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initMenuData();

        MenuAdapter adp = new MenuAdapter(getActivity(), menuList);
        recyclerView.setAdapter(adp);


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

/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        EntryItem item = (EntryItem) items.get(position);

    } */

    public void initMenuData() {

        menuList = new ArrayList<>();

        int[] menuType = new int[] {0, 1, 2, 0, 1, 1, 1, 0, 2, 1, 0, 1, 1, 1, 1, 3, 1, 3};
        String[] menuTitle = new String[] {"Profile", "Profile", "Notifications", "Advisor", "Compare Courses",
                "Country Information", "Need Help", "Purchases", "My Credits", "Purchase History", "Settings", "Notification Settings",
                "Terms and Conditions", "Privacy & Policy", "Currency Converter", "", "Logout", ""};
        int[] menuIcons = new int[] {0, R.mipmap.menu_profile, R.mipmap.menu_notifications, 0, R.mipmap.menu_compare_courses,
                R.mipmap.menu_country_information, R.mipmap.menu_need_help, 0, R.mipmap.menu_my_credits, R.mipmap.menu_purchase_history,
                0, R.mipmap.menu_notifications_settings, R.mipmap.menu_terms_and_conditions, R.mipmap.menu_privacy_and_policy,
                R.mipmap.menu_currency_converter, 0, R.mipmap.menu_logout, 0};
        String[] notifications = new String[]{"", "", "60", "", "", "", "", "", creditBalance, "", "", "",
                "", "", "", "", "", ""};

        for (int i = 0; i < menuType.length; i++) {

            mModel = new MenuModel();

            mModel.setRowType(menuType[i]);
            mModel.setMenuIcon(menuIcons[i]);
            mModel.setMenuTitle(menuTitle[i]);
            mModel.setMenuNotifications(notifications[i]);

            menuList.add(mModel);

        }


    }

}
