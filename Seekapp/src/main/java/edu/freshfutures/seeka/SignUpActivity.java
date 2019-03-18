package edu.freshfutures.seeka;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import edu.freshfutures.seeka.volley.custom.application.AppController;

public class SignUpActivity extends AppCompatActivity {

    private SignUpTask mAuthTask = null;
    private View mProgressView;
    private EditText mNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mSignUpFormView;
    private View mUnderlineName;
    private View mUnderlineEmail;
    private View mUnderlinePassword;

    private static String TAG = SignUpActivity.class.getSimpleName();
    protected static String TOKENID = "token_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageButton btnBackArrow = (ImageButton) findViewById(R.id.SignUpBtnBackArrow);

        mNameView = (EditText) findViewById(R.id.txtName);
        mEmailView = (EditText) findViewById(R.id.txtEmail);
        mPasswordView = (EditText) findViewById(R.id.txtPassword);
        mSignUpFormView = findViewById(R.id.SignupForm);
        mProgressView = findViewById(R.id.signup_progress);
        mUnderlineName = findViewById(R.id.name_underline);
        mUnderlineEmail = findViewById(R.id.email_underline);
        mUnderlinePassword = findViewById(R.id.pword_underline);
        Button mSignUpButton = (Button) findViewById(R.id.buttonSignUp);


        if (btnBackArrow != null) {
            btnBackArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    SignUpActivity.this.finish();
                }
            });
        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                    attemptSignUp();
                    return true;
                }

                return false;
            }
        });

        mNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v == mNameView) {

                    if (hasFocus) {
                        mUnderlineName.setBackgroundColor(Color.parseColor("#5DBEE5"));
                    } else {
                        mUnderlineName.setBackgroundColor(Color.parseColor("#BEC8C8"));
                    }
                }
            }
        });

        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v == mEmailView) {

                    if (hasFocus) {
                        mUnderlineEmail.setBackgroundColor(Color.parseColor("#5DBEE5"));
                    } else {
                        mUnderlineEmail.setBackgroundColor(Color.parseColor("#BEC8C8"));
                    }
                }
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v == mPasswordView) {

                    if (hasFocus) {
                        mUnderlinePassword.setBackgroundColor(Color.parseColor("#5DBEE5"));
                    } else {
                        mUnderlinePassword.setBackgroundColor(Color.parseColor("#BEC8C8"));
                    }
                }
            }
        });


        if (mSignUpButton != null) {
            mSignUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isNetworkAvailable()) {
                        attemptSignUp();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Internet network is not available. Connect and try again!", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    private void makeJsonObjectRequest(String username, String email, String password) {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UserService/registration";
        JSONObject obj = new JSONObject();

        try {
            obj.put("userName", username);
            obj.put("email", email);
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

                try {
                    status = response.getString("status");
                    token = response.getString("sessionToken");
                    msg = response.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status != null) {
                    if(status.equals("1")) {

                        SharedPreferences sharedPref = SignUpActivity.this.getSharedPreferences(
                                getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(TOKENID, token);
                        editor.apply();

                      /*  Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        SignUpActivity.this.startActivity(intent);
                        SignUpActivity.this.finish(); */

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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

        // return jsonResponse;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) SignUpActivity.this
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

    public void attemptSignUp() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for alphabets in name characters, if the user entered one.
        if (TextUtils.isEmpty(email)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if  (!isNameValid(name)){
            mNameView.setError(getString(R.string.error_invalid_name));
            focusView = mNameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
          /*  showProgress(true);
            mAuthTask = new SignUpTask(name, email, password);
            mAuthTask.execute((Void) null); */

            makeJsonObjectRequest(name, email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private boolean isNameValid(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class SignUpTask extends AsyncTask<Void, Void, Boolean> {


        private final String mName;
        private final String mEmail;
        private final String mPassword;

        SignUpTask(String name, String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                SignUpActivity.this.startActivity(intent);
                SignUpActivity.this.finish();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
