package edu.freshfutures.seeka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class FacultyActivity extends AppCompatActivity {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    Context context;

    ArrayList<Integer> facultyIcons;
    JSONArray jsonIcons;
    int position;

    RelativeLayout btnAgric;
    RelativeLayout btnArchiBuilding;
    RelativeLayout btnAviation;
    RelativeLayout btnBizFinAcc;
    RelativeLayout btnComSc;
    RelativeLayout btnArtsDesign;
    RelativeLayout btnEducation;
    RelativeLayout btnEngineering;
    RelativeLayout btnHumanities;
    RelativeLayout btnLangLiterature;
    RelativeLayout btnLaw;
    RelativeLayout btnMassComm;
    RelativeLayout btnMedNursing;
    RelativeLayout btnHealthSc;
    RelativeLayout btnHotelMngmnt;

    ImageView imgAgric;
    ImageView imgArchiBuilding;
    ImageView imgAviation;
    ImageView imgBizFinAcc;
    ImageView imgComSc;
    ImageView imgArtsDesign;
    ImageView imgEducation;
    ImageView imgEngineering;
    ImageView imgHumanities;
    ImageView imgLangLiterature;
    ImageView imgLaw;
    ImageView imgMassComm;
    ImageView imgMedNursing;
    ImageView imgHealthSc;
    ImageView imgHotelMngmnt;

    TextView txtAgric;
    TextView txtArchiBuilding;
    TextView txtAviation;
    TextView txtBizFinAcc;
    TextView txtComSc;
    TextView txtArtsDesign;
    TextView txtEducation;
    TextView txtEngineering;
    TextView txtHumanities;
    TextView txtLangLiterature;
    TextView txtLaw;
    TextView txtMassComm;
    TextView txtMedNursing;
    TextView txtHealthSc;
    TextView txtHotelMngmnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        customBar = getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(this);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_filter_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setTitle("Faculty");

        facultyIcons = new ArrayList<>();

        ImageButton btnConfirm = (ImageButton) customView.findViewById(R.id.imgFilterAccept);
        ImageButton btnCancel = (ImageButton) customView.findViewById(R.id.imgFilterCancel);

        btnAgric = (RelativeLayout) findViewById(R.id.btnAgric);
        btnArchiBuilding = (RelativeLayout) findViewById(R.id.btnArchiBuilding);
        btnAviation = (RelativeLayout) findViewById(R.id.btnAviation);
        btnBizFinAcc = (RelativeLayout) findViewById(R.id.btnBizFinAcc);
        btnComSc = (RelativeLayout) findViewById(R.id.btnCompSc);
        btnArtsDesign = (RelativeLayout) findViewById(R.id.btnArtsDesign);
        btnEducation = (RelativeLayout) findViewById(R.id.btnEducation);
        btnEngineering = (RelativeLayout) findViewById(R.id.btnEngineering);
        btnHumanities = (RelativeLayout) findViewById(R.id.btnHumanities);
        btnLangLiterature = (RelativeLayout) findViewById(R.id.btnLangLitereature);
        btnLaw = (RelativeLayout) findViewById(R.id.btnLaw);
        btnMassComm = (RelativeLayout) findViewById(R.id.btnMassComm);
        btnMedNursing = (RelativeLayout) findViewById(R.id.btnMedNursing);
        btnHealthSc = (RelativeLayout) findViewById(R.id.btnHealthSc);
        btnHotelMngmnt = (RelativeLayout) findViewById(R.id.btnHotelMngmnt);

        imgAgric = (ImageView) findViewById(R.id.imgAgric);
        imgArchiBuilding = (ImageView) findViewById(R.id.imgArchiBuilding);
        imgAviation = (ImageView) findViewById(R.id.imageBtnAviation);
        imgBizFinAcc = (ImageView) findViewById(R.id.imgBizFinAcc);
        imgComSc = (ImageView) findViewById(R.id.imgCompSc);
        imgArtsDesign = (ImageView) findViewById(R.id.imageBtnArtsDesign);
        imgEducation = (ImageView) findViewById(R.id.imgEducation);
        imgEngineering = (ImageView) findViewById(R.id.imgEngineering);
        imgHumanities = (ImageView) findViewById(R.id.imageBtnHumanities);
        imgLangLiterature = (ImageView) findViewById(R.id.imgLiterature);
        imgLaw = (ImageView) findViewById(R.id.imgLaw);
        imgMassComm = (ImageView) findViewById(R.id.imgMassComm);
        imgMedNursing = (ImageView) findViewById(R.id.imgMedNursing);
        imgHealthSc = (ImageView) findViewById(R.id.imgHealthSc);
        imgHotelMngmnt = (ImageView) findViewById(R.id.imgHotelMngmt);

        txtAgric = (TextView) findViewById(R.id.txtAgric);
        txtArchiBuilding = (TextView) findViewById(R.id.txtArchiBuilding);
        txtAviation = (TextView) findViewById(R.id.txtAvaition);
        txtBizFinAcc = (TextView) findViewById(R.id.txtBizFinAcc);
        txtComSc = (TextView) findViewById(R.id.txtCompSc);
        txtArtsDesign = (TextView) findViewById(R.id.txtArtsDesign);
        txtEducation = (TextView) findViewById(R.id.txtEducation);
        txtEngineering = (TextView) findViewById(R.id.txtEngineering);
        txtHumanities = (TextView) findViewById(R.id.txtHumanities);
        txtLangLiterature = (TextView) findViewById(R.id.txtLiterature);
        txtLaw = (TextView) findViewById(R.id.txtLaw);
        txtMassComm = (TextView) findViewById(R.id.txtMassComm);
        txtMedNursing = (TextView) findViewById(R.id.txtMedNursing);
        txtHealthSc = (TextView) findViewById(R.id.txtHealthSc);
        txtHotelMngmnt = (TextView) findViewById(R.id.txtHotelMngmnt);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonIcons = new JSONArray(facultyIcons);

                SharedPreferences settings = getSharedPreferences("PREFFACULTYSELECTION", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("fscultySelections", jsonIcons.toString());
                editor.apply();

                Intent intent = new Intent(FacultyActivity.this, FilterActivity.class);
                startActivity(intent);
                FacultyActivity.this.finish();
            }
        });

        btnAgric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 1;

                if(txtAgric.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgAgric.setImageResource(R.mipmap.agriculture_artboard_selected);
                    txtAgric.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.agriculture_artboard_selected);
                }

                else {
                    imgAgric.setImageResource(R.mipmap.agriculture_artboard);
                    txtAgric.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }

            }
        });

        btnArchiBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 2;

                if(txtArchiBuilding.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgArchiBuilding.setImageResource(R.mipmap.architecture_building_plan_artboard_selected);
                    txtArchiBuilding.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.architecture_building_plan_artboard_selected);
                }

                else {
                    imgArchiBuilding.setImageResource(R.mipmap.architecture_building_plan_artboard);
                    txtArchiBuilding.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }

            }
        });

        btnAviation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 3;

                if(txtAviation.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgAviation.setImageResource(R.mipmap.aviation_artboard_selected);
                    txtAviation.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.aviation_artboard_selected);
                }

                else {
                    imgAviation.setImageResource(R.mipmap.aviation_artboard);
                    txtAviation.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }

            }
        });

        btnBizFinAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 4;

                if(txtBizFinAcc.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgBizFinAcc.setImageResource(R.mipmap.business_finance_accounting_artboard_selected);
                    txtBizFinAcc.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.business_finance_accounting_artboard_selected);
                }

                else {
                    imgBizFinAcc.setImageResource(R.mipmap.business_finance_accounting_artboard);
                    txtBizFinAcc.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }
            }
        });

        btnComSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 5;

                if(txtComSc.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgComSc.setImageResource(R.mipmap.computer_science_it_artboard_selected);
                    txtComSc.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.computer_science_it_artboard_selected);
                }

                else {
                    imgComSc.setImageResource(R.mipmap.computer_science_artboard);
                    txtComSc.setTextColor(Color.parseColor("#3c4141"));

                    //facultyIcons.remove(position);
                }
            }
        });

        btnArtsDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 6;

                if(txtArtsDesign.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgArtsDesign.setImageResource(R.mipmap.creative_arts_design_artboard_selected);
                    txtArtsDesign.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.creative_arts_design_artboard_selected);
                }

                else {
                    imgArtsDesign.setImageResource(R.mipmap.creative_arts_design_artboard);
                    txtArtsDesign.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 7;

                if(txtEducation.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgEducation.setImageResource(R.mipmap.education_artboard_selected);
                    txtEducation.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.education_artboard_selected);
                }

                else {
                    imgEducation.setImageResource(R.mipmap.education_artboard);
                    txtEducation.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }
            }
        });

        btnEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 8;

                if(txtEngineering.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgEngineering.setImageResource(R.mipmap.engineering_artboard_selected);
                    txtEngineering.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(R.mipmap.engineering_artboard_selected);
                }

                else {
                    imgEngineering.setImageResource(R.mipmap.engineering_artboard);
                    txtEngineering.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }
            }
        });

        btnHumanities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 9;

                if(txtHumanities.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgHumanities.setImageResource(R.mipmap.humanities_social_science_artboard_selected);
                    txtHumanities.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.humanities_social_science_artboard_selected);
                }

                else {
                    imgHumanities.setImageResource(R.mipmap.humanities_social_science_artboard);
                    txtHumanities.setTextColor(Color.parseColor("#3c4141"));

                   // facultyIcons.remove(position);
                }
            }
        });


        btnLangLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 10;

                if(txtLangLiterature.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgLangLiterature.setImageResource(R.mipmap.language_literature_artboard_selected);
                    txtLangLiterature.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.language_literature_artboard_selected);
                }

                else {
                    imgLangLiterature.setImageResource(R.mipmap.language_literature_artboard);
                    txtLangLiterature.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });

        btnLaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 11;

                if(txtLaw.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgLaw.setImageResource(R.mipmap.law_artboard_selected);
                    txtLaw.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.law_artboard_selected);
                }

                else {
                    imgLaw.setImageResource(R.mipmap.law_artboard);
                    txtLaw.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });

        btnMassComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 12;

                if(txtMassComm.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgMassComm.setImageResource(R.mipmap.mass_communication_media_artboard_selected);
                    txtMassComm.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.mass_communication_media_artboard_selected);
                }

                else {
                    imgMassComm.setImageResource(R.mipmap.mass_communication_media_artboard);
                    txtMassComm.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });

        btnMedNursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 13;

                if(txtMedNursing.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgMedNursing.setImageResource(R.mipmap.medical_nursing_artboard_selected);
                    txtMedNursing.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.medical_nursing_artboard_selected);
                }

                else {
                    imgMedNursing.setImageResource(R.mipmap.medical_nursing_artboard);
                    txtMedNursing.setTextColor(Color.parseColor("#3c4141"));

                 //   facultyIcons.remove(position);
                }
            }
        });

        btnHealthSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 14;

                if(txtHealthSc.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgHealthSc.setImageResource(R.mipmap.science_health_science_artboard_selected);
                    txtHealthSc.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.science_health_science_artboard_selected);
                }

                else {
                    imgHealthSc.setImageResource(R.mipmap.science_health_science_artboard);
                    txtHealthSc.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });

        btnHotelMngmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = 15;

                if(txtHotelMngmnt.getCurrentTextColor() == Color.parseColor("#3c4141")) {
                    imgHotelMngmnt.setImageResource(R.mipmap.tourism_hotel_management_artboard_selected);
                    txtHotelMngmnt.setTextColor(Color.parseColor("#0091f0"));

                    facultyIcons.add(position, R.mipmap.tourism_hotel_management_artboard_selected);
                }

                else {
                    imgHotelMngmnt.setImageResource(R.mipmap.tourism_hotel_management_artboard);
                    txtHotelMngmnt.setTextColor(Color.parseColor("#3c4141"));

                  //  facultyIcons.remove(position);
                }
            }
        });


    }

    public void setTitle(String title) {

        actionBarTitle = (TextView) customView.findViewById(R.id.txtFilterTitle);
        actionBarTitle.setText(title);
    }
}
