package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_username;
    private EditText et_email, et_usertname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Reference();

        tv_username.setText(DataClass.GlobalUser.getUsername());
        et_usertname.setText(DataClass.GlobalUser.getUsername());
        et_email.setText(DataClass.GlobalUser.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Reference (){
        tv_username = findViewById(R.id.tvemail_profile);
        et_email = findViewById(R.id.etemail_profile);
        et_usertname = findViewById(R.id.etusername_profile);
    }
}
