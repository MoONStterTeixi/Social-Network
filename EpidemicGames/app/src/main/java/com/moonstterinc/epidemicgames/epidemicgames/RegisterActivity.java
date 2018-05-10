package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etusername, etmail, etpassword, etreppassword;
    private RadioGroup rggenre;
    private RadioButton rbother, rbfemale, rbmale;
    private CheckBox cbaccept;

    int resultRG;

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

    public void Reference(){
        etusername = findViewById(R.id.username);
        etmail = findViewById(R.id.email);
        etpassword = findViewById(R.id.password);
        etreppassword = findViewById(R.id.repPassword);

        rggenre = findViewById(R.id.genre);

        rbother = findViewById(R.id.other);
        rbfemale = findViewById(R.id.female);
        rbmale = findViewById(R.id.male);

        cbaccept = findViewById(R.id.accept);
    }

    //Selecionar el Grupo de radio
    public void selectRadioGroup() {
        int radioButtonID = rggenre.getCheckedRadioButtonId();
        View radioButton = rggenre.findViewById(radioButtonID);
        resultRG = rggenre.indexOfChild(radioButton);
    }

    public void goLogin (View v) throws InterruptedException {
        if (etpassword.getText().toString().equals(etreppassword.getText().toString())){
            selectRadioGroup();

            User usr = new User(etusername.getText().toString(),etmail.getText().toString(),etpassword.getText().toString(),resultRG,cbaccept.isChecked());
            new Connection().execute("http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=register&json="+usr.toJson());
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent Intent = new Intent(this, LoginActivity.class);
            startActivity(Intent);
            finish();

        }else{
            Toast.makeText(this, "Passwords: ¡Are not the same!", Toast.LENGTH_LONG).show();
        }
<<<<<<< HEAD
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
=======
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
    }
    //Boton lateral atras <-
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
