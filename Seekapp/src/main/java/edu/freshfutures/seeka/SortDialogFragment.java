package edu.freshfutures.seeka;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import edu.freshfutures.seeka.adapters.SortPopupAdapter;

/**
 * Created by tokmang on 4/18/2016.
 */
public class SortDialogFragment extends DialogFragment {

    ListView lisSort;
    Button sortConfirm;
    Context ctx;

    public String[] sortTitles;
    ArrayList<String> sortValues = new ArrayList<>();
    Dialog d;

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;



    public SortDialogFragment() {
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

        View view = inflater.inflate(R.layout.sort_popup_structure, container, false);
        ctx = getActivity();

        lisSort = (ListView) view.findViewById(R.id.lisSortPopup);
        sortConfirm = (Button) view.findViewById(R.id.btnSortConfirm);

        sortTitles = getResources().getStringArray(R.array.sort_text_array);
        Collections.addAll(sortValues, sortTitles);

        lisSort.setAdapter(new SortPopupAdapter(ctx, sortValues));

        ImageButton cancel = (ImageButton) view.findViewById(R.id.imgSortCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogCancelClick(SortDialogFragment.this);
             //   FragmentUnlock.sortUnlocked.setImageResource(R.mipmap.sort_locked);
            //    FragmentUnlock.txtTabSort.setTextColor(Color.parseColor("#646969"));
                d.cancel();
            }
        });

        sortConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogConfirmClick(SortDialogFragment.this);
              //  Fragment.sortUnlocked.setImageResource(R.mipmap.sort_locked);
            //    FragmentUnlock.txtTabSort.setTextColor(Color.parseColor("#646969"));
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogs");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    public interface NoticeDialogListener {
        public void onDialogConfirmClick(DialogFragment dialog);
        public void onDialogCancelClick(DialogFragment dialog);
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}

