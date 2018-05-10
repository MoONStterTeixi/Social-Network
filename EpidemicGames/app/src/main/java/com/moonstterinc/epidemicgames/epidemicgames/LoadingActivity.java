package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    //Inicializamos variables
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        hideNavigationBar();
        ResultisNetworkAvailable();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //Ocultar Barra de navegacion e notific
    private void hideNavigationBar(){
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    //Pantalla de carga de 10s OK
    public void thread(final int time){
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(time);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    if (value == 0){
                        StartActivity();
                    }else{
                        NoConnActivity();
                    }
                }
            }
        };
        welcomeThread.start();
    }

    //Selecion de Activity
    private void StartActivity(){
        Intent i = new Intent(LoadingActivity.this, StartActivity.class);
        startActivity(i);
        finish();
    }
    private void NoConnActivity(){
        Intent i = new Intent(LoadingActivity.this, NoConnActivity.class);
        startActivity(i);
        finish();
    }

    //Comprueba si hay internet DATOS O WIFI
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Muestra mensaje vía toast (OK/FAIL)
    private void ResultisNetworkAvailable(){
        if (isNetworkAvailable() == true) {
            Toast.makeText(this, "STATUS INTERNET: OK", Toast.LENGTH_SHORT).show();

            value = 0;
            thread(2000);

        } else {
            Toast.makeText(this, "STATUS INTERNET: FAIL", Toast.LENGTH_SHORT).show();

            value = 1;
            thread(1000);
        }
    }
}
