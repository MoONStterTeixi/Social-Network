package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_email ,et_pwd, et_repwd;
    //private RadioGroup rggenre;
    private RadioButton rbother, rbfemale, rbmale;
    private CheckBox cb_accept;

    int resultRG;
    String msg = null;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Reference();

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

    public void Reference(){
        et_username = findViewById(R.id.username);
        et_email = findViewById(R.id.email);
        et_pwd = findViewById(R.id.password);
        et_repwd = findViewById(R.id.repPassword);

        //rggenre = findViewById(R.id.genre);

        /*rbother = findViewById(R.id.other);
        rbfemale = findViewById(R.id.female);
        rbmale = findViewById(R.id.male);*/

        cb_accept = findViewById(R.id.accept);

    }


    //Selecionar el Grupo de radio
   /* public void selectRadioGroup() {
        int radioButtonID = rggenre.getCheckedRadioButtonId();
        View radioButton = rggenre.findViewById(radioButtonID);
        resultRG = rggenre.indexOfChild(radioButton);
    }*/

    private static final String PASSWORD_REGEXP =
            "^"
                    + "(?=.*\\d)"
                    + "(?=.*[a-z])"
                    + "(?=.*[A-Z])"
                    + "(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])"
                    + "."
                    + "{6,15}"
                    + "$";


    private Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
    private Matcher matcher;

    public boolean isValid(String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    //Return true if email is valid and false if email is invalid
    protected boolean validateEmail(String email) {
        String emailPattern = DataClass.pattern;
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public void goLogin (View v) throws InterruptedException {
        //selectRadioGroup();
        if(!validateEmail(et_email.getText().toString())) {
            et_email.setError("Invalid Email");
            et_email.requestFocus();
        } else if (!isValid(et_pwd.getText().toString())) {
            et_pwd.setError("Invalid Password");
            et_pwd.requestFocus();
        } else if (et_pwd.getText().toString().equals(et_repwd.getText().toString())){
            User usr = new User(et_username.getText().toString(), et_email.getText().toString(), et_pwd.getText().toString(),resultRG ,cb_accept.isChecked());
            //new Connection().execute("http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=register&json="+usr.toJson());
            new CallAPI_Rest().execute("https://moonstterinc.000webhostapp.com/SN/query.php?action=register&json="+usr.toJson());
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent Intent = new Intent(this, LoginActivity.class);
            startActivity(Intent);
            finish();
        }else{
            Toast.makeText(this, "Passwords: ¡Are not the same!", Toast.LENGTH_LONG).show();
            et_pwd.requestFocus();

            et_repwd.setError("Are not the same");
            et_repwd.requestFocus();
        }
    }
}
