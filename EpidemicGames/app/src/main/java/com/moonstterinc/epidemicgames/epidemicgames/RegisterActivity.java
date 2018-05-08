package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Button btgoback, btgologin;

    int resultRG;
    String msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Reference();
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

        btgoback = findViewById(R.id.goBack);
        btgologin = findViewById(R.id.goLogin);
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

            DataClass.email= etmail.getText().toString();
            DataClass.pwd = etpassword.getText().toString();
            String link = "AQUÍ LA IP";
            new ClassCallPHPfile().execute(link);
            msg = DataClass.msg;

            Toast.makeText(this, "[Status:] " + msg, Toast.LENGTH_LONG).show();

            /*User usr = new User(etusername.getText().toString(),etmail.getText().toString(),etpassword.getText().toString(),resultRG,cbaccept.isChecked());
            Connection conn = new Connection("register", usr);
            Toast.makeText(this, "JSON:" + usr.toJson(), Toast.LENGTH_LONG).show();
            conn.start();
            conn.join();*/
            //Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            /*Intent Intent = new Intent(this, LoginActivity.class);
            startActivity(Intent);
            finish();*/
        }else{
            Toast.makeText(this, "Passwords: ¡Are not the same!", Toast.LENGTH_LONG).show();
        }


    }

    public void goBack (View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
    }

}
