package edu.freshfutures.seeka;


import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.LockedModel;
import edu.freshfutures.seeka.adapters.LockedAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class LockedFragment extends Fragment {

    Context context;
    static public AppCompatActivity activity;


    static RecyclerView recyclerView;

    private static final String ARG_SECTION_TITLE = "section_title";
    private String title;

    ImageButton toolSettings;
    ImageButton btnBack;
    TextView txtTitle;

    FragmentManager fm;
    FragmentTransaction ft;

    private FragmentSearch fragmentSearch;

    public static ArrayList<LockedModel> lockedList;

    public LockedFragment() {
        // Required empty public constructor
    }

    public static LockedFragment newInstance(String title) {
        LockedFragment fragment = new LockedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ARG_SECTION_TITLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_locked, container, false);

        context = getActivity();

        Toolbar parent = (Toolbar) view.findViewById(R.id.toolbars);
        ((HomeActivity) getActivity()).setSupportActionBar(parent);

        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        assert parent != null;
        toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);
        btnBack = (ImageButton) parent.findViewById(R.id.btnDialogBack);

        fragmentSearch = FragmentSearch.newInstance(1, "Search");
        txtTitle = (TextView) view.findViewById(R.id.txtHomeTitle);

        txtTitle.setText(title);

        ImageButton sortSearchResult = (ImageButton) view.findViewById(R.id.imgBtnResultSort);
        ImageButton notifications = (ImageButton) view.findViewById(R.id.imgLockedNotify);
        Button btnSearchFilter = (Button) view.findViewById(R.id.btnSearchFilters);

      /*  if (isNetworkAvailable()) {
            if(!sessionId.equals("")) {

            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "Could not make request due to no session ID", Toast.LENGTH_LONG).show();
            }

        } else {

            Toast.makeText(getActivity(), "Internet network is not available. Connect and try again!", Toast.LENGTH_LONG).show();

        }*/


        recyclerView = (RecyclerView) view.findViewById(R.id.lisSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LockedAdapter adapter = new LockedAdapter(getActivity(), lockedList);
        recyclerView.setAdapter(adapter);

        sortSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
                SortDialogFragment newFragments = new SortDialogFragment();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "dialogSort");

            }
        });

        btnSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
                LockedNotiDialogFragment lockedFragments = new LockedNotiDialogFragment();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                lockedFragments.show(transactions, "lockedFragments");

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, fragmentSearch, "SEARCH_FRAGMENT");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
