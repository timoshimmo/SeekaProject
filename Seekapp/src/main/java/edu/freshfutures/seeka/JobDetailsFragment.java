package edu.freshfutures.seeka;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.freshfutures.seeka.interfaces.ToolBarBack;


public class JobDetailsFragment extends AppCompatActivity implements ToolBarBack {

    Context ctx;

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;

    LinearLayout btnShortSkilled;
    LinearLayout btnCompHiring;
    LinearLayout btnInternship;
    LinearLayout btnJobsites;

    private String tags;

    public JobDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_job_details);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        View v = customBar.getCustomView();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);

        ctx = JobDetailsFragment.this;

        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);
        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);
        toolSettings = (ImageButton) customView.findViewById(R.id.imgHomeMenu);
        actionBarTitle = (TextView) customView.findViewById(R.id.txtHomeTitle);

        setTitle("Job Details");

        btnShortSkilled = (LinearLayout) findViewById(R.id.btnShortSkilled);
        btnCompHiring = (LinearLayout) findViewById(R.id.btnCompHiring);
        btnInternship = (LinearLayout) findViewById(R.id.btnInternship);
        btnJobsites = (LinearLayout) findViewById(R.id.btnPopularSites);


        btnShortSkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                ToolBarBack toolBarCallBack = (ToolBarBack) ctx;

                String tag = "dialogsShortSkilled";
                FragmentManager fragmentManager = ((JobDetailsFragment)ctx).getSupportFragmentManager();
                ShortSkilledFragment shSkilledFragments = new ShortSkilledFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, shSkilledFragments, tag)
                        .addToBackStack(null).commit();

                toolBarCallBack.switchBackVisible(vs, ivs);
                toolBarCallBack.stringTag(tag);


            }
        });

        btnCompHiring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                ToolBarBack toolBarCallBack = (ToolBarBack) ctx;

                String tag = "dialogsCompshiring";
                FragmentManager fragmentManager = ((JobDetailsFragment)ctx).getSupportFragmentManager();
                CompaniesHireFragment compsHireFragments = new CompaniesHireFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, compsHireFragments, tag)
                        .addToBackStack(null).commit();

                toolBarCallBack.switchBackVisible(vs, ivs);
                toolBarCallBack.stringTag(tag);

            }
        });

        btnInternship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                ToolBarBack toolBarCallBack = (ToolBarBack) ctx;

                String tag = "dialogsInternships";
                FragmentManager fragmentManager = ((JobDetailsFragment)ctx).getSupportFragmentManager();
                FragmentJobInterships jobsInterships = new FragmentJobInterships();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, jobsInterships, tag)
                        .addToBackStack(null).commit();

                toolBarCallBack.switchBackVisible(vs, ivs);
                toolBarCallBack.stringTag(tag);
            }
        });

        btnJobsites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                ToolBarBack toolBarCallBack = (ToolBarBack) ctx;

                String tag = "dialogsJobSites";
                FragmentManager fragmentManager = ((JobDetailsFragment)ctx).getSupportFragmentManager();
                PopularJobFragment popJobsFragments = new PopularJobFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                transaction.add(android.R.id.content, popJobsFragments, tag)
                        .addToBackStack(null).commit();

                toolBarCallBack.switchBackVisible(vs, ivs);
                toolBarCallBack.stringTag(tag);
            }
        });


        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        dialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vs = View.VISIBLE;
                int ivs = View.INVISIBLE;

                switchBackInvisible(vs, ivs);

                String fragTag = getStringTag();
                DialogFragment dialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag(fragTag);

                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }

                setTitle("Job Details");

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

    public void setTitle(String title) {

        actionBarTitle.setText(title);
    }


    @Override
    public void switchBackVisible(int vs, int ivs) {
        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(toolbarBack.getVisibility() == vs) {
            toolbarBack.setVisibility(ivs);
            dialogBack.setVisibility(vs);
        }
    }

    @Override
    public void switchBackInvisible(int vs, int ivs) {

        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);

        if(dialogBack.getVisibility() == vs) {
            dialogBack.setVisibility(ivs);
            toolbarBack.setVisibility(vs);
        }
    }

    @Override
    public void stringTag(String t) {
        this.tags = t;
    }

    public String getStringTag() {
        return this.tags;
    }
}
