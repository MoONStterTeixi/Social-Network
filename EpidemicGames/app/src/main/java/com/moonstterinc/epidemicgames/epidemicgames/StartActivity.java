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

    public void goRegisterv2 (View v){
        Intent Intent = new Intent(this, Registerv2Activity.class);
        startActivity(Intent);
    }

    //Delete una vez pasado los parametros
    public void goRegisterOld  (View v){
        Intent Intent = new Intent(this, RegisterActivity.class);
        startActivity(Intent);
    }

    public void terms(View v){
        android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(this);
        myBuild.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed turpis ex, feugiat at dolor quis," +
                " elementum rutrum lorem. Morbi viverra leo eget scelerisque imperdiet. Nulla sit amet commodo enim.\n" +
                " Sed congue sed diam sit amet pharetra. Vestibulum vitae nulla eu metus varius venenatis ac nec urna.\n\n\n\n" +
                " Quisque ut lorem tempus orci porttitor porttitor nec in mauris. Ut sem velit, semper et enim viverra, " +
                "porttitor posuere ligula. Sed tincidunt, risus et bibendum lobortis, nunc nisl pellentesque quam, in vulputate felis tortor ac justo.\n\n\n" +
                " In ornare ex vitae eros ornare commodo. Vestibulum porttitor, felis in hendrerit porttitor, ligula quam tincidunt mi," +
                " eu condimentum urna neque ac eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                "Sed et tortor mollis, convallis mauris nec, pellentesque eros."+
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed turpis ex, feugiat at dolor quis," +
                " elementum rutrum lorem. Morbi viverra leo eget scelerisque imperdiet. Nulla sit amet commodo enim.\n" +
                " Sed congue sed diam sit amet pharetra. Vestibulum vitae nulla eu metus varius venenatis ac nec urna.\n\n\n\n" +
                " Quisque ut lorem tempus orci porttitor porttitor nec in mauris. Ut sem velit, semper et enim viverra, " +
                "porttitor posuere ligula. Sed tincidunt, risus et bibendum lobortis, nunc nisl pellentesque quam, in vulputate felis tortor ac justo.\n\n\n" +
                " In ornare ex vitae eros ornare commodo. Vestibulum porttitor, felis in hendrerit porttitor, ligula quam tincidunt mi," +
                " eu condimentum urna neque ac eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                "Sed et tortor mollis, convallis mauris nec, pellentesque eros.");

        myBuild.setTitle("Epidemic Games Info");

        myBuild.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        android.app.AlertDialog dialog = myBuild.create();
        dialog.show();
    }

}
