package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
<<<<<<< HEAD

        //Evitar que rote *
=======
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goLogin (View v){
        Intent Intent = new Intent(this, LoginActivity.class);
        startActivity(Intent);
    }

    public void goRegister (View v){
        Intent Intent = new Intent(this, RegisterActivity.class);
        startActivity(Intent);
    }
}
