package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by tokmang on 4/19/2016.
 */
public class EmailDialogFragment extends DialogFragment {

    Context ctx;
    Button emailSend;
    EditText txtEmail;
    ImageButton imgCancel;
    Dialog d;


    public EmailDialogFragment() {
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
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.producea4_layout_structure, container, false);

        emailSend = (Button) view.findViewById(R.id.btnEmailSend);
        txtEmail = (EditText) view.findViewById(R.id.popupEmailAdd);
        imgCancel = (ImageButton) view.findViewById(R.id.imgEmailCancel);

        ctx = getActivity();

        emailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUnlock.produceA4.setImageResource(R.mipmap.unlock_producea4_icon);
                FragmentUnlock.txtTabProduce.setTextColor(Color.parseColor("#646969"));
                Dismiss();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUnlock.produceA4.setImageResource(R.mipmap.unlock_producea4_icon);
                FragmentUnlock.txtTabProduce.setTextColor(Color.parseColor("#646969"));
                FragmentUnlock.set = false;
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsEmail");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }
}
