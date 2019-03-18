package edu.freshfutures.seeka;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import edu.freshfutures.seeka.adapters.SettingsPopupAdapter;


public class SettingsPopup extends DialogFragment {

    ListView lisSettings;
    ImageButton close;
    Context ctx;

    public String[] settingsTitles;
    Dialog d;



    public SettingsPopup() {
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
        View view = inflater.inflate(R.layout.settings_layout_structure, container, false);

        lisSettings = (ListView) view.findViewById(R.id.listSettings);
        close = (ImageButton) view.findViewById(R.id.imgSettingsClose);

        settingsTitles = getResources().getStringArray(R.array.settings_text_array);

        lisSettings.setAdapter(new SettingsPopupAdapter(getActivity(), settingsTitles));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        lisSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*
                switch(position) {

                    case 0:

                        break;

                    case 1:

                        break;

                    case 2:

                        break;

                    case 3:

                        break;
                }

                */

            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("settingsDialogs");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }


}
