package edu.freshfutures.seeka;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {

    private static final String CONFIG_CLIENT_ID = "AYmG6dY2Glf5U6XC3ZcUx09KfLlaxmkIjihckBY2KATTqO-WuayD2S2RJ65jacDwVFNeYJtyKqP1gTeI";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
    .environment(CONFIG_ENVIRONMENT)
    .clientId(CONFIG_CLIENT_ID)
// the following are only used in PayPalFuturePaymentActivity.
    .merchantName("Hipster Store")
    .merchantPrivacyPolicyUri(
            Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
            Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        Bundle extras = getIntent().getExtras();

        Button order = (Button) findViewById(R.id.order);
        TextView cost = (TextView) findViewById(R.id.txtCostValue);
        TextView price = (TextView) findViewById(R.id.txtCreditPriceValue);

        if(extras != null) {
            if (cost != null) {
                cost.setText(extras.getString("cost"));
            }

            if (price != null) {
                price.setText(extras.getString("price"));
            }
        }

        if (order != null) {
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thingToBuy = new PayPalPayment(new BigDecimal("10"), "USD",
                            "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(PayPalActivity.this,
                            PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            });
        }
    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(PayPalActivity.this,
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
