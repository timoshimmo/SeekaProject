package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by tokmang on 4/21/2016.
 */
public class UnlockCourseDialogFragment extends DialogFragment {

    Button uCourseConfirm;
    Button freeCredits;
    ImageButton btnClose;
    Context ctx;

    Dialog d;

    int cid;
    String courseId;
    protected static String TOKENID = "token_id";

    private static final String ARG_COURSEID = "courseID";
    private static String TAG = HomeActivity.class.getSimpleName();

    public static UnlockCourseDialogFragment newInstance(int id) {
        UnlockCourseDialogFragment fragment = new UnlockCourseDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COURSEID, id);

        fragment.setArguments(args);
        return fragment;
    }

    public UnlockCourseDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);

        cid = getArguments().getInt(ARG_COURSEID);
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
        View view = inflater.inflate(R.layout.unlock_course_popup_structure, container, false);

        ctx = getActivity();

        uCourseConfirm = (Button) view.findViewById(R.id.btnUnlockPopupConfirm);
        freeCredits = (Button) view.findViewById(R.id.btnUnlockCourseFree);
        btnClose = (ImageButton) view.findViewById(R.id.btnCloseUnlock);

        uCourseConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeJsonObjectRequest();

                SharedPreferences prefunlockedData = getActivity().getSharedPreferences("PREFUNLOCKEDDATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor unlockedEditor = prefunlockedData.edit();
                unlockedEditor.putString("unlockedData", "[]");
                unlockedEditor.apply();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        return view;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("unlockCoursesFragments");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_UNLOCK_LOCKED_COURSES;

        JSONObject obj = new JSONObject();

        courseId = String.valueOf(cid);

        SharedPreferences getSession = getActivity().getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        SharedPreferences getBalance = getActivity().getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
        final String creditBal = getBalance.getString("creditBalance", "");

        final String creditPoints = "1";

        try {

            obj.put("sessionId", session);
            obj.put("courseId", courseId);
            obj.put("coursePoints", creditPoints);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                int deductPoints = Integer.valueOf(creditPoints);
                int currentPoints = Integer.valueOf(creditBal);

                int totalPoints = currentPoints - deductPoints;
                String message;

                if(totalPoints > 0) {
                    SharedPreferences prefCreditBal = getActivity().getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor balEditor = prefCreditBal.edit();
                    balEditor.putString("creditBalance", String.valueOf(totalPoints));
                    balEditor.apply();

                }

                try {
                    message = response.getString("sessionToken");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
