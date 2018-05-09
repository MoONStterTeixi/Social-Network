package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    }

    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
        tx_login = findViewById(R.id.login);
    }

    public void goBack (View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
    }

    public void goWelcome (View v){
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
    }

}
