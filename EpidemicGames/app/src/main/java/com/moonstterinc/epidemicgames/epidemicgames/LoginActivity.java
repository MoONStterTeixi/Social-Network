package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pwd;
    private Button go;
    //private ClassCallPHPfile callPHP = new ClassCallPHPfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Reference();

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Toast.makeText(this, callPHP.ok, Toast.LENGTH_SHORT).show();
    }

    private void Reference(){
        email = findViewById(R.id.email);
        pwd =  findViewById(R.id.pwd);
        go = findViewById(R.id.go);
    }

    //Boton lateral atras <-
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Boton lateral atras <-
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void goWelcome (View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
        startActivity(Intent);
        finish();

    }

}
