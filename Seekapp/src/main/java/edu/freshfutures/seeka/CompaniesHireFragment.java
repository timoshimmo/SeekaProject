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


public class CompaniesHireFragment extends DialogFragment {

    String[] employer;
    String[] sector;
    Dialog d;


    public CompaniesHireFragment() {
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

        View view = inflater.inflate(R.layout.fragment_companies_hire, container, false);

        ((JobDetailsFragment)getActivity()).setTitle("Companies That Hire");

        TableLayout tableHire = (TableLayout)view.findViewById(R.id.compHireTable);

        employer = new String[] {"Maybank", "Air Asia"};
        sector = new String[] {"Banking & Financial Services", "Leisure, Travel & Hospitality, Transportation/Logistics"};

        for(int i = 0; i < employer.length; i++) {

            TableRow row = (TableRow)LayoutInflater.from(getActivity()).inflate(R.layout.company_hiring_tabrow, null);

            ((CustomTextViewMedium)row.findViewById(R.id.txtTitleBox)).setText(employer[i]);
            ((CustomTextViewLight)row.findViewById(R.id.txtDescBox)).setText(sector[i]);

            tableHire.addView(row);
        }

        tableHire.requestLayout();

        return view;
    }


}
