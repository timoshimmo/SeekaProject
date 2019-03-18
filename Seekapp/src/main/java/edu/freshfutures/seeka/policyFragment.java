package edu.freshfutures.seeka;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by tokmang on 4/28/2016.
 */
public class policyFragment extends DialogFragment {

    ActionBar customBar;
    TextView actionBarTitle;
    View customView;
    ImageButton toolbarCancel;

    Context ctx;
    Dialog d;

    public policyFragment() {
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
        View view = inflater.inflate(R.layout.terms_policy_layout, container, false);

        ctx = getActivity();

        customBar = ((HomeActivity)ctx).getSupportActionBar();
        LayoutInflater customLayout = LayoutInflater.from(ctx);

        customBar.setDisplayShowHomeEnabled(false);
        customBar.setDisplayShowTitleEnabled(false);
        customBar.setDisplayShowCustomEnabled(true);

        customView = customLayout.inflate(R.layout.custom_terms_bar, null);
        customBar.setCustomView(customView);

        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        actionBarTitle = (TextView) customView.findViewById(R.id.txtTermsTitle);
        toolbarCancel = (ImageButton) customView.findViewById(R.id.imgTermsCancel);

        toolbarCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("termsFragment");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }
}
