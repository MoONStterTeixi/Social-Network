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

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goLogin (View v){
        Intent Intent = new Intent(this, LoginActivity.class);
        startActivity(Intent);
    }

    public void goRegisterv2 (View v){
        Intent Intent = new Intent(this, Registerv2Activity.class);
        startActivity(Intent);
    }

    //Delete una vez pasado los parametros
    public void goRegisterOld  (View v){
        Intent Intent = new Intent(this, RegisterActivity.class);
        startActivity(Intent);
    }
}
