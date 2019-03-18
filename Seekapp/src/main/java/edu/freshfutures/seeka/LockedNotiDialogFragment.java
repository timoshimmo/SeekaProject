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

/**
 * Created by tokmang on 4/20/2016.
 */
public class LockedNotiDialogFragment extends DialogFragment {

    Button lNotifyConfirm;
    Context ctx;

    Dialog d;

    // Use this instance of the interface to deliver action events
    LockedNotifyDialogListener mListener;



    public LockedNotiDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogTheme);
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
        View view = inflater.inflate(R.layout.locked_notification_dialog_structure, container, false);

        ctx = getActivity();

        lNotifyConfirm = (Button) view.findViewById(R.id.btnLNotifyConfirm);


        Typeface secface= Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Regular.otf");

        lNotifyConfirm.setTypeface(secface);

        lNotifyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogConfirmClick(LockedNotiDialogFragment.this);
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("lockedFragments");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    public interface LockedNotifyDialogListener {
        public void onDialogConfirmClick(DialogFragment dialog);
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LockedNotifyDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
