package edu.freshfutures.seeka;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartActivityFragmentOne extends Fragment {

    public StartActivityFragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_start_activity_fragment_one, container, false);

        final Button btnGetStarted = (Button) rootView.findViewById(R.id.ButtonGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }
}
