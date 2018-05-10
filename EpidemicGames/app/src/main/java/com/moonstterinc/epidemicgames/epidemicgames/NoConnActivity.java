package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NoConnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_conn);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }



    public void goLoading (View v){
        Intent Intent = new Intent(this, LoadingActivity.class);
        startActivity(Intent);
        finish();
    }

    public void onlyDEV(View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
        finish();
    }
}
