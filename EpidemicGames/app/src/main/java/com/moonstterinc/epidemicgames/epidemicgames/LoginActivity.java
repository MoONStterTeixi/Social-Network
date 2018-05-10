package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;

    //private ClassCallPHPfile callPHP = new ClassCallPHPfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Reference();

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
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
    Toast.makeText(this, "Error User", Toast.LENGTH_SHORT).show();
       // User usr = new User(et_email.getText().toString(),et_pwd.getText().toString());
        //new Connection().execute("http://172.17.129.63:80/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json="+usr.toJson());

        /*if (DataClass.UserJson.equals('0') ) {
            Toast.makeText(this, "Error Pass", Toast.LENGTH_SHORT).show();
            //if (DataClass.UserJson.equals('1')){
            //Toast.makeText(this, "Error User", Toast.LENGTH_SHORT).show();
        }else{
            Intent Intent = new Intent(this, WelcomeActivity.class);
            startActivity(Intent);
            finish();
        }*/
    }

    public void onlyDEV(View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
        startActivity(Intent);
        finish();
    }

}
