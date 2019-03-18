package edu.freshfutures.seeka;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetailedGradeRequirements extends DialogFragment {

    Dialog d;

    Context ctx;

    ImageButton btnBack;
    ImageButton btnSettings;

    String requirements;

    TextView reqirementDetails;

    private static final String ARG_COURSEREQ = "CourseReq";

    public static FragmentDetailedGradeRequirements newInstance(String reqCourse) {
        FragmentDetailedGradeRequirements fragment = new FragmentDetailedGradeRequirements();
        Bundle args = new Bundle();
        args.putString(ARG_COURSEREQ, reqCourse);

        fragment.setArguments(args);
        return fragment;
    }

    public FragmentDetailedGradeRequirements() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);

        requirements = getArguments().getString(ARG_COURSEREQ);
    }

    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_detailed_grade_requirements, container, false);

        ctx = getActivity();

        btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        btnSettings = (ImageButton) view.findViewById(R.id.btnSettings);

        reqirementDetails = (TextView) view.findViewById(R.id.txtReqDetails);


        reqirementDetails.setText(requirements);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((HomeActivity) ctx).getSupportFragmentManager();
                SettingsPopup newFragments = new SettingsPopup();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "settingsDialogs");
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsGradeReq");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }


}
