package edu.freshfutures.seeka;


import android.app.Dialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CtryAboutDialogFragment extends DialogFragment {

    Dialog d;
    private static final String keyArray = "arrayKey";
    ArrayList<String> aboutData;

    int ctryBackImage;

    public CtryAboutDialogFragment() {
        // Required empty public constructor
    }

    public static CtryAboutDialogFragment newInstance() {
        CtryAboutDialogFragment fragment = new CtryAboutDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_ctry_about_dialog, container, false);

        TextView txtArea = (TextView) view.findViewById(R.id.txtAboutArea);
        TextView txtPopulation = (TextView) view.findViewById(R.id.txtAboutPopulation);
        TextView txtCapCity = (TextView) view.findViewById(R.id.txtAboutCapitalCity);
        TextView txtPeople = (TextView) view.findViewById(R.id.txtAboutPeople);
        TextView txtLanguage = (TextView) view.findViewById(R.id.txtAboutLanguage);
        TextView txtReligion = (TextView) view.findViewById(R.id.txtAboutReligion);
        TextView txtGDP = (TextView) view.findViewById(R.id.txtAboutGDP);
        TextView txtGDPCap = (TextView) view.findViewById(R.id.txtAboutGDPCap);
        TextView txtAnnualGrowth = (TextView) view.findViewById(R.id.txtAboutAnnualGrowth);
        TextView txtInflation = (TextView) view.findViewById(R.id.txtAboutInflation);
        TextView txtMajorIndustries = (TextView) view.findViewById(R.id.txtAboutMajorIndustries);
        TextView txtMajorTrading = (TextView) view.findViewById(R.id.txtAboutMajorTrading);
        TextView txtHeadState = (TextView) view.findViewById(R.id.txtAboutHeadGov);

        aboutData = new ArrayList<>();
        aboutData = CountryDetailsActivity.moreData;

        ImageView ctryImage = (ImageView) view.findViewById(R.id.imgCountryInfo);
        ctryBackImage = CountryDetailsActivity.ctryBackImage;
        ctryImage.setImageResource(ctryBackImage);

        txtArea.setText(aboutData.get(0));
        txtPopulation.setText(aboutData.get(1));
        txtCapCity.setText(aboutData.get(2));
        txtPeople.setText(aboutData.get(3));
        txtLanguage.setText(aboutData.get(4));
        txtReligion.setText(aboutData.get(5));
        txtHeadState.setText(aboutData.get(6));
        txtGDP.setText(aboutData.get(7));
        txtGDPCap.setText(aboutData.get(8));
        txtAnnualGrowth.setText(aboutData.get(9));
        txtInflation.setText(aboutData.get(10));
        txtMajorIndustries.setText(aboutData.get(11));
        txtMajorTrading.setText(aboutData.get(12));



        return view;
    }


}
