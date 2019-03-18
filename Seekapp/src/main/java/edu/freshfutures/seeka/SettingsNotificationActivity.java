package edu.freshfutures.seeka;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsNotificationActivity extends AppCompatActivity {

    ImageButton toolbarBack;
    ImageButton toolSettings;

    ListView lisNotification;
    Context ctx;

    public String[] notificationTitles;
    private static String TAG = CountryDetailsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification);

        Toolbar parent = (Toolbar) findViewById(R.id.notifyToolbars);
        setSupportActionBar(parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarBack = (ImageButton) parent.findViewById(R.id.imgHomeBack);
        toolSettings = (ImageButton) parent.findViewById(R.id.imgHomeMenu);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds interfaces to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
