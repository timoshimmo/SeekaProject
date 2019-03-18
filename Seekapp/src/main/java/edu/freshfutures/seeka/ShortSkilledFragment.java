package edu.freshfutures.seeka;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import edu.freshfutures.seeka.CustomWidgets.CustomTextViewLight;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewMedium;


public class ShortSkilledFragment extends DialogFragment {

    String[] positions;
    String[] skillsRequired;
    Dialog d;

    public ShortSkilledFragment() {
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
        View view = inflater.inflate(R.layout.fragment_short_skilled, container, false);

        ((JobDetailsFragment)getActivity()).setTitle("Short Skilled Jobs");

        TableLayout tableHistory = (TableLayout)view.findViewById(R.id.short_skilled_table);

        positions = new String[] {"Senior Accountants", "Senior Managers", "Senior Managers", "Project Managers", "Project Managers"};
        skillsRequired = new String[] {"finance, banking, statistics, auditing, insurance", "financial, communications and other business services",
                "health, education, social and community services and membership organizations", "trade, broadcasting and other services",
                "construction, transportation, production and utilities"};

        for(int i = 0; i < positions.length; i++) {

            TableRow row = (TableRow)LayoutInflater.from(getActivity()).inflate(R.layout.short_skilled_table_structure, null);

            ((CustomTextViewMedium)row.findViewById(R.id.txtTitleBox)).setText(positions[i]);
            ((CustomTextViewLight)row.findViewById(R.id.txtDescBox)).setText(skillsRequired[i]);

            tableHistory.addView(row);
        }

        tableHistory.requestLayout();


        return view;
    }


}
