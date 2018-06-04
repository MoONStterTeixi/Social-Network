package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.DialogInterface;
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

    //Delete una vez pasado los parametros
    public void goRegisterOld  (View v){
        Intent Intent = new Intent(this, RegisterActivity.class);
        startActivity(Intent);
    }

    public void terms(View v){
        android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(this);
        myBuild.setMessage("Sin información.");

        myBuild.setTitle("[Info] Epidemic Games ");

        myBuild.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        android.app.AlertDialog dialog = myBuild.create();
        dialog.show();
    }

}
