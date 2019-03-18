package edu.freshfutures.seeka;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        String username;

        Bundle extras = getIntent().getExtras();

        ImageButton btnSearch = (ImageButton) findViewById(R.id.btnNewUserSearch);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btnWelcomeUserClose);
        TextView txtUsername =  (TextView) findViewById(R.id.txtWelcomeUserText);

        if(extras != null) {
            username = extras.getString("LOGGED_USER");
            assert txtUsername != null;
            txtUsername.setText(username);
        }

        else {

            assert txtUsername != null;
            txtUsername.setText(R.string.default_username);

        }

        assert btnSearch != null;
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewUserActivity.this, HomeActivity.class);
                intent.putExtra("tab_no", 1);
                startActivity(intent);
                NewUserActivity.this.finish();

            }
        });

        assert btnCancel != null;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewUserActivity.this, HomeActivity.class);
                intent.putExtra("tab_no", 0);
                startActivity(intent);
                NewUserActivity.this.finish();

            }
        });
    }

}