package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by tokmang on 7/3/2016.
 */
public class DateDialogFragment extends DialogFragment {

    DatePicker dp;
    Button btnDateCancel, btnSetDate;

    Dialog d;

    public DateDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog);

    }


    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
        if (d!=null){

            d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Resources res = getResources();

            final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
            final View titleDivider = d.findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(R.color.white_color));
            }
        //    d.setTitle("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.datepicker_dialog_layout, container, false);

        btnDateCancel = (Button) view.findViewById(R.id.btnCancelDate);
        btnSetDate = (Button) view.findViewById(R.id.btnSetDate);

        dp = (DatePicker) view.findViewById(R.id.datePicker);

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dayOfMonth = dp.getDayOfMonth();
                int monthOfYear = dp.getMonth() + 1;
                int year = dp.getYear();

                String date = String.format(Locale.US, "%s/%s/%s", dayOfMonth, monthOfYear, year);
                FragmentProfileInfo.txtEditBirthday.setText(date);

                dismiss();
            }
        });

        btnDateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }


}
