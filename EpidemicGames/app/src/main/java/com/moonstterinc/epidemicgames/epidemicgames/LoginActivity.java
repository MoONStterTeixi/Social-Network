package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
    private TextView tx_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Reference();
        DataClass.context = this.getApplicationContext();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    //Boton lateral atras <-
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
        tx_login = findViewById(R.id.login);
    }

    public void goWelcome (View v){
        try{
            DataClass.usr = new User(et_email.getText().toString(), et_pwd.getText().toString());
            new CallAPI_Rest().execute("http://172.17.129.63:80/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json=" + DataClass.usr.toJsonL()).get();
            tx_login.setText(DataClass.UserJson);
        }catch(Exception e){
            Toast.makeText(this, "[ERROR] User o Password", Toast.LENGTH_LONG).show();
        }
        if (DataClass.UserJson.equals("0") || DataClass.UserJson.equals("1") || DataClass.UserJson.equals("")) {
            Toast.makeText(this, "[ERROR] Turn new", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent Intent = new Intent(this, WelcomeActivity.class);
            startActivity(Intent);
            finish();
        }
    }

    public void onlyDEV(View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
        startActivity(Intent);
        finish();
    }
}
