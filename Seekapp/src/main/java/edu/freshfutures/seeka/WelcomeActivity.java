package edu.freshfutures.seeka;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Facebook Login Success!");
                        String accessToken = loginResult.getAccessToken()
                                .getToken();

                        SharedPreferences sharedPrefss = WelcomeActivity.this
                                .getSharedPreferences(
                                        "FBToken",
                                        Context.MODE_PRIVATE);
                        SharedPreferences.Editor editors = sharedPrefss
                                .edit();
                        editors.putString("ACCESS_TOKEN", accessToken);
                        editors.apply();


                    }

                    @Override
                    public void onCancel() {

                        System.out.println("Facebook Login Cancel!");

                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_welcome);



        TextView textTitle = (TextView) findViewById(R.id.txtWelcomeTitle);
        TextView textFBLgInfo = (TextView) findViewById(R.id.txtFBLoginInfo);
        TextView textfbButton = (TextView) findViewById(R.id.txtFButton);
        TextView textEmailInfo = (TextView) findViewById(R.id.txtEmailinfo);
        TextView textUagreement = (TextView) findViewById(R.id.txtUserAgreement);
        TextView textClkUagreement = (TextView) findViewById(R.id.txtClickableAgreement);
        final Button redirectRegister = (Button) findViewById(R.id.btnRegisterRedirect);
        final Button redirectLogin = (Button) findViewById(R.id.btnLoginRedirect);
        RelativeLayout btnFacebook = (RelativeLayout) findViewById(R.id.btnFacebook);

        ImageButton backButton = (ImageButton) findViewById(R.id.btnBackArrow);

        Typeface secface= Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/Roboto-Light.ttf");
        Typeface secTitleface= Typeface.createFromAsset(WelcomeActivity.this.getAssets(), "fonts/Roboto-Medium.ttf");

        textFBLgInfo.setTypeface(secface);
        textfbButton.setTypeface(secface);
        textEmailInfo.setTypeface(secface);
        textUagreement.setTypeface(secface);

        textTitle.setTypeface(secTitleface);
        textClkUagreement.setTypeface(secTitleface);


        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this,
                        Arrays.asList("public_profile"));
            }
        });


        redirectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        redirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
