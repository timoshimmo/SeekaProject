package edu.freshfutures.seeka;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Build;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

public class LoginActivity extends AppCompatActivity  {

    private View mProgressView;
    private EditText mPasswordView;
    private EditText mUsernameView;
    private View mLoginFormView;
    private View mUnderlineEmail;
    private View mUnderlinePassword;

    private static String TAG = LoginActivity.class.getSimpleName();
    protected static String TOKENID = "token_id";

    EduQualificationDB databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton btnBackArrow = (ImageButton) findViewById(R.id.btnBackArrow);

        mUsernameView = (EditText) findViewById(R.id.editTxtUsername);
        mPasswordView = (EditText) findViewById(R.id.editTxtPassword);
        mLoginFormView = findViewById(R.id.LoginForm);
        mProgressView = findViewById(R.id.login_progress);
        mUnderlineEmail = findViewById(R.id.email_underline);
        mUnderlinePassword = findViewById(R.id.password_underline);
        Button mLoginButton = (Button) findViewById(R.id.btnLogin);

        databaseHelper = new EduQualificationDB(LoginActivity.this);

        if (btnBackArrow != null) {
            btnBackArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            });
        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }

                return false;
            }
        });

        mUsernameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v == mUsernameView) {

                    if (hasFocus) {
                        mUnderlineEmail.setBackgroundColor(Color.parseColor("#00aff0"));
                    } else {
                        mUnderlineEmail.setBackgroundColor(Color.parseColor("#C3C8C8"));
                    }
                }
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v == mPasswordView) {

                    if (hasFocus) {
                        mUnderlinePassword.setBackgroundColor(Color.parseColor("#00aff0"));
                    } else {
                        mUnderlinePassword.setBackgroundColor(Color.parseColor("#C3C8C8"));
                    }
                }
            }
        });


        if (mLoginButton != null) {
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isNetworkAvailable()) {
                        attemptLogin();

                    } else {

                        Toast.makeText(LoginActivity.this, "Internet network is not available. Connect and try again!", Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
    }

    private void makeJsonObjectRequest(final String username, String password) {

        String requestURL = Url.URL_LOGIN;
        JSONObject obj = new JSONObject();

        try {
            obj.put("userName", username);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                String status = null;
                String token = null;
                String msg = null;

                JSONObject data;
                JSONArray eligibilityArray;

                try {
                    status = response.getString("status");
                    token = response.getString("sessionToken");
                    msg = response.getString("message");

                    data = response.getJSONObject("data");

                    SharedPreferences prefCreditBal = getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor balEditor = prefCreditBal.edit();
                    balEditor.putString("creditBalance", data.getString("balPoints"));
                    balEditor.apply();

                    SharedPreferences sharedStatus = getSharedPreferences("PREFPROFILESTATUS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor statusEditor = sharedStatus.edit();
                    statusEditor.putString("profileStatus", data.getString("profileStatus"));
                    statusEditor.apply();

                    int quaCount = databaseHelper.getEligibilityCount();

                    if(quaCount < 1) {

                        if(!data.isNull("eligbilityList")) {

                            eligibilityArray = data.getJSONArray("eligbilityList");

                            int isEligible = 0;
                            JSONObject eligibilityMessage = new JSONObject(loadEligJSONFromAsset());

                            String quaEduLevel = data.getString("eduLevel");

                            SharedPreferences settings = getSharedPreferences("PREFQUAEDULEVEL", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("quaEduLevel", quaEduLevel);
                            editor.apply();

                            for (int i = 0; i < eligibilityArray.length(); i++) {

                                JSONObject eliObj = eligibilityArray.getJSONObject(i);

                                String countryCode = eliObj.getString("countryName");
                                String courseType = eliObj.getString("courseType");
                                int statusCode = eliObj.getInt("status");

                                String quaCode = String.valueOf(statusCode);
                                String quaMessage = eligibilityMessage.getString(quaCode);

                                if(quaCode.equals("2")) {
                                    isEligible = 1;
                                }

                                databaseHelper.insertEduSystemData(countryCode, courseType, isEligible, quaMessage, statusCode);

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status != null) {
                    if(status.equals("1")) {

                        SharedPreferences getFirstLogged = getSharedPreferences("PREFFIRSTLOGGED", Context.MODE_PRIVATE);
                        int firstLoggedStatus = getFirstLogged.getInt("FIRST_LOGGED", 0);

                        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences(
                                getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(TOKENID, token);
                        editor.apply();

                        SharedPreferences prefCreditBal = getSharedPreferences("PREFHOMEDATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor balEditor = prefCreditBal.edit();
                        balEditor.putString("homeData", "[]");
                        balEditor.apply();

                        if(firstLoggedStatus == 0) {
                            Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                            intent.putExtra("LOGGED_USER", username);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                        }

                      else {

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();

                        }


                    }

                    else {
                        Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                showProgress(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) LoginActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }


    public void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String uname = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(uname)) {
            mUsernameView.setError("This field is required");
            focusView = mUsernameView;
            cancel = true;
        } else if (!isEmailValid(uname)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            makeJsonObjectRequest(uname, password);
        }
    }

    private boolean isEmailValid(String username) {
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public String loadEligJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("eligibility/EligibilityPopup.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
