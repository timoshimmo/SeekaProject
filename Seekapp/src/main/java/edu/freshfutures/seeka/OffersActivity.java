package edu.freshfutures.seeka;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class OffersActivity extends AppCompatActivity {

    Context ctx;
    ActionBar customBar;
    TextView actionBarTitle;
    View customView;

    ImageButton toolbarBack;
    ImageButton dialogBack;
    ImageButton toolSettings;

    RadioButton rbAdvisor;
    RadioButton rbContactUni;
    RadioButton rbFullPack;
    RadioButton rbSelfPack;

    Button nextPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

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

        ctx = OffersActivity.this;

        toolbarBack = (ImageButton) customView.findViewById(R.id.imgHomeBack);
        dialogBack = (ImageButton) customView.findViewById(R.id.btnDialogBack);
        toolSettings = (ImageButton) customView.findViewById(R.id.imgHomeMenu);
        actionBarTitle = (TextView) customView.findViewById(R.id.txtHomeTitle);

        nextPage = (Button) findViewById(R.id.btnNext);

        setTitle("Offers");

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

        Typeface font = Typeface.createFromAsset(OffersActivity.this.getAssets(), "fonts/Roboto-Light.ttf");

        rbAdvisor  = (RadioButton) findViewById(R.id.rdRequestAdvisor);
        rbAdvisor.setTypeface(font);

        rbContactUni = (RadioButton) findViewById(R.id.rdContactUni);
        rbContactUni.setTypeface(font);

        rbFullPack = (RadioButton) findViewById(R.id.rdFullPack);
        rbFullPack.setTypeface(font);

        rbSelfPack = (RadioButton) findViewById(R.id.rdSelfPack);
        rbSelfPack.setTypeface(font);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffersActivity.this, SubmitOfferActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setTitle(String title) {
        actionBarTitle.setText(title);
    }

}
