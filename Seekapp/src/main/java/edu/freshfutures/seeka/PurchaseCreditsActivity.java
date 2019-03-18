package edu.freshfutures.seeka;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseCreditsActivity extends AppCompatActivity {

    LinearLayout fiveCredits;
    LinearLayout fifteenCredits;
    LinearLayout thirtyFiveCredits;
    LinearLayout fiftyCredits;
    LinearLayout hundredCredits;
    LinearLayout OneFiftyCredits;

    AlertDialog alertDialog;

    RadioGroup paymentGroup;
    RadioButton rdPaypal, rdGoogle, rdCheque;
    Button btnPaymentCancel, btnPaymentOk;

    String paypalValue;
    String googleValue;
    String chequeValue;

    TextView fivePrice;
    TextView fifteenPrice;
    TextView thirtyFivePrice;
    TextView fiftyPrice;
    TextView hundredPrice;
    TextView OneFiftyPrice;

    TextView txtCreditBalance;

    String method;
    String costValue;
    String CreditsValue;

    String transID;

    ImageButton toolbarBack;
    ImageButton toolSettings;

    private static String TAG = PurchaseCreditsActivity.class.getSimpleName();

    protected static String TOKENID = "token_id";

    private static final String CONFIG_CLIENT_ID = "AYmG6dY2Glf5U6XC3ZcUx09KfLlaxmkIjihckBY2KATTqO-WuayD2S2RJ65jacDwVFNeYJtyKqP1gTeI";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
// the following are only used in PayPalFuturePaymentActivity.
            .merchantName("Seeka Credits")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_fragment_layout);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        fiveCredits = (LinearLayout) findViewById(R.id.btnFiveCredits);
        fifteenCredits = (LinearLayout) findViewById(R.id.btnFifteenCredits);
        thirtyFiveCredits = (LinearLayout) findViewById(R.id.btnThirtyFiveCredits);
        fiftyCredits = (LinearLayout) findViewById(R.id.btnFiftyCredits);
        hundredCredits = (LinearLayout) findViewById(R.id.btnHundredCredits);
        OneFiftyCredits = (LinearLayout) findViewById(R.id.btnOneFiftyCredits);

        fivePrice = (TextView) findViewById(R.id.txtFivePrice);
        fifteenPrice = (TextView) findViewById(R.id.txtfifteenPrice);
        thirtyFivePrice = (TextView) findViewById(R.id.txtThirtyFivePrice);
        fiftyPrice = (TextView) findViewById(R.id.txtFiftyPrice);
        hundredPrice = (TextView) findViewById(R.id.txtHundredPrice);
        OneFiftyPrice = (TextView) findViewById(R.id.txtOneFiftyPrice);

        txtCreditBalance = (TextView) findViewById(R.id.txtCreditsBalance);

        Toolbar parent = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarBack = (ImageButton) (parent != null ? parent.findViewById(R.id.imgHomeBack) : null);
        toolSettings = (ImageButton) (parent != null ? parent.findViewById(R.id.imgHomeMenu) : null);

        SharedPreferences getBalance = getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
        String creditBal = getBalance.getString("creditBalance", "");

        txtCreditBalance.setText(creditBal);

        paymentDialog();

        fiveCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = fivePrice.getText().toString();
                CreditsValue = "5";
                alertDialog.show();

            }
        });

        fifteenCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = fifteenPrice.getText().toString();
                CreditsValue = "15";
                alertDialog.show();

            }
        });

        thirtyFiveCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = thirtyFivePrice.getText().toString();
                CreditsValue = "35";
                alertDialog.show();

            }
        });

        fiftyCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = fiftyPrice.getText().toString();
                CreditsValue = "50";
                alertDialog.show();

            }
        });

        hundredCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = hundredPrice.getText().toString();
                CreditsValue = "100";
                alertDialog.show();

            }
        });

        OneFiftyCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costValue = OneFiftyPrice.getText().toString();
                CreditsValue = "150";
                alertDialog.show();

            }
        });

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        toolSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsPopup newFragments = new SettingsPopup();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "settingsDialogs");
            }
        });




    }

    public void paymentDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseCreditsActivity.this);
        LayoutInflater inflaters = this.getLayoutInflater();

        View dialogView = inflaters.inflate(R.layout.payment_method_dialog, null);
        builder.setView(dialogView);

        btnPaymentCancel = (Button) dialogView.findViewById(R.id.btnCancelPayment);
        btnPaymentOk = (Button) dialogView.findViewById(R.id.btnAcceptPayment);

        paymentGroup = (RadioGroup) dialogView.findViewById(R.id.rdPaymentGroup);

        rdPaypal = (RadioButton) dialogView.findViewById(R.id.radioPaypal);
        rdGoogle = (RadioButton) dialogView.findViewById(R.id.radioPlayStore);
        rdCheque = (RadioButton) dialogView.findViewById(R.id.radioCheque);

        alertDialog = builder.create();

        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioPaypal) {
                    paypalValue = "PayPal";
                } else if (checkedId == R.id.radioPlayStore) {
                    googleValue = "Play Store";

                } else {
                    chequeValue = "Cheque";
                }

            }
        });

        btnPaymentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnPaymentOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = paymentGroup.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if (selectedId == rdPaypal.getId()) {

                    method = "PayPal";
                    transID = "PAL0121345";
                } else if (selectedId == rdGoogle.getId()) {

                    method = "Play Store";
                    transID = "PLS0121345";
                } else {

                    method = "Cheque";
                    transID = "CHQ0121345";
                }

                makeJsonObjectRequest();
                alertDialog.dismiss();

            }
        });

    }

    private void makeJsonObjectRequest() {

        String requestURL = Url.URL_UPDATE_CREDITS;
        JSONObject obj = new JSONObject();

        SharedPreferences getSession = getSharedPreferences(getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date

        try {
            obj.put("sessionToken", session);
            obj.put("creditPoints", CreditsValue);
            obj.put("transactionRefId", transID);
            obj.put("paymentMethod", method);
            obj.put("purchaseDate", currentDateTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                String msg = null;
                int points;

                try {

                    msg = response.getString("message");
                    points = response.getInt("creditPoints");

                    SharedPreferences prefCreditBal = getSharedPreferences("PREFCREDITBALANCE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor balEditor = prefCreditBal.edit();
                    balEditor.putString("creditBalance", String.valueOf(points));
                    balEditor.apply();

                    txtCreditBalance.setText(String.valueOf(points));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(PurchaseCreditsActivity.this, msg, Toast.LENGTH_LONG).show();

                thingToBuy = new PayPalPayment(new BigDecimal(costValue), "USD",
                        "Seeka Credits", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(PurchaseCreditsActivity.this,
                        PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(PurchaseCreditsActivity.this,
                PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));
                        Toast.makeText(getApplicationContext(), "Order placed",
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));
                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);
                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }
    public void onFuturePaymentPurchasePressed(View pressed) {
// Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);
        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);
// TODO: Send correlationId and transaction details to your server for
// processing with
// PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }
    @Override
    public void onDestroy() {
// Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


}
