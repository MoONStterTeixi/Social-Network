package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static com.moonstterinc.epidemicgames.epidemicgames.DataClass.UserJson;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
    private Button bt_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Reference();

        //Toast.makeText(this, callPHP.ok, Toast.LENGTH_SHORT).show();
    }

    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
        bt_go = findViewById(R.id.go);
    }

    public void goBack (View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
    }

    public void goWelcome (View v){

        User usr = new User(et_email.getText().toString(),et_pwd.getText().toString());
        new Connection().execute("http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json="+usr.toJson());
        Toast.makeText(this, "JSON: "+usr.toJson(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "DATACLASS: "+UserJson, Toast.LENGTH_LONG).show();
        if (DataClass.UserJson == "Error!"){
            Toast.makeText(this, "Errooooor", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
           /* Intent Intent = new Intent(this, WelcomeActivity.class);
            startActivity(Intent);
            finish();*/

        }


    }

}
