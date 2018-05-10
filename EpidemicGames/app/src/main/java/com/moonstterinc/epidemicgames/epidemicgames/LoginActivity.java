package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.widget.TextView;
import android.widget.Toast;

>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
<<<<<<< HEAD

    //private ClassCallPHPfile callPHP = new ClassCallPHPfile();
=======
    private TextView tx_login;
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Reference();
<<<<<<< HEAD

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


=======
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
    }

    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
<<<<<<< HEAD
=======
        tx_login = findViewById(R.id.login);
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
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
<<<<<<< HEAD
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
=======
        User usr = new User(et_email.getText().toString(),et_pwd.getText().toString());
        new Connection().execute("http://172.17.129.63:80/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json="+usr.toJson());
        tx_login.setText(DataClass.UserJson);
        Toast.makeText(this, "XXXXXXXXXXXXX", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "JSON: "+usr.toJson(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "DATACLASS: "+DataClass.UserJson, Toast.LENGTH_LONG).show();
        /*if (DataClass.UserJson.equals('0') ) {
            Toast.makeText(this, "Error Pass", Toast.LENGTH_SHORT).show();
        }else if (DataClass.UserJson.equals('1')){
            Toast.makeText(this, "Error User", Toast.LENGTH_SHORT).show();
        }*/

        /*else{
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(this, WelcomeActivity.class);
            startActivity(Intent);
        }*/
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
    }

}
