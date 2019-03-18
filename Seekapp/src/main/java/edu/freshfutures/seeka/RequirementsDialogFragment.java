package edu.freshfutures.seeka;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.freshfutures.seeka.Models.LockedModel;

/**
 * Created by tokmang on 4/20/2016.
 */
public class RequirementsDialogFragment extends DialogFragment {

    Button rNotifyConfirm;
    Context ctx;

    Dialog d;
    String remarks;

    private static final String ARG_REMARKS_MESSAGE = "locked_remarks";

    public static RequirementsDialogFragment newInstance(String remarks) {
        RequirementsDialogFragment fragment = new RequirementsDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REMARKS_MESSAGE, remarks);

        fragment.setArguments(args);
        return fragment;
    }

    public RequirementsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogTheme);

        remarks = getArguments().getString(ARG_REMARKS_MESSAGE);
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
        View view = inflater.inflate(R.layout.requirements_dialog_structure, container, false);

        ctx = getActivity();

        TextView txtReqDetails = (TextView) view.findViewById(R.id.txtReqText1);
        rNotifyConfirm = (Button) view.findViewById(R.id.btnReqConfirm);

        txtReqDetails.setText(remarks);

        rNotifyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("reqFragments");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

}
