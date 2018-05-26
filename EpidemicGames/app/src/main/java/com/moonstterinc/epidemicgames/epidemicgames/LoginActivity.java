package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.moonstterinc.epidemicgames.epidemicgames.DataClass.context;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
    private ToggleButton tb_pwd;

    //Guardar Credenciales

    private Switch s_saveLogin;
    Dialog myDialog;

    //Instaciamos
    private ProgressDialog progressDialog;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "email";
    public static final String PWD = "pwd";
    public static final String SWITCH = "switch";

    private String email;
    private String pwd;
    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDialog = new Dialog(this);

        Reference();
        DataClass.context = this.getApplicationContext();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loadData();

        s_saveLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    saveData();
                    Toast.makeText(context, "¡Credenciales guardadas!", Toast.LENGTH_LONG).show();
                }
                else {
                    et_email.setText(null);
                    et_pwd.setText(null);
                    s_saveLogin.setChecked(false);
                    saveData();
                    Toast.makeText(context, "¡Credenciales eliminas!", Toast.LENGTH_LONG).show();
                }
            }
        });

        loadData();
        updateViews();

        //Inicializamos la barra
        progressDialog= new ProgressDialog(this);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
        s_saveLogin = findViewById(R.id.saveLogin);
        tb_pwd = findViewById(R.id.toggleButton);
    }

    //Ver Password
    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            et_pwd.setTransformationMethod(null);
        } else {
            et_pwd.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    //Guardar Datos
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, et_email.getText().toString().replace(" ",""));
        editor.putString(PWD, et_pwd.getText().toString().replace(" ",""));
        editor.putBoolean(SWITCH, s_saveLogin.isChecked());

        editor.apply();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL,"");
        pwd = sharedPreferences.getString(PWD, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH, false);
    }

    public void updateViews(){
        et_email.setText(email);
        et_pwd.setText(pwd);
        s_saveLogin.setChecked(switchOnOff);
    }


    //Restablecer contraseña
    public void ShowEmail(View v) {
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.login_change_pass);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");
            btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
    }

    //Crear la tarjeta del progresso
    public void ShowProgress(){
        //agregas un mensaje en el ProgressDialog
        progressDialog.setTitle("Validando usuario");
        progressDialog.setMessage("Iniciado sesión");
        //muestras el ProgressDialog
        progressDialog.show();
    }

    public void goWelcome (View v){
        //Encryptar password con Email y password
        String pwdFinal = CryptoHash.getSha256(et_pwd.getText().toString().replace(" ",""));
        String userFinal = CryptoHash.getSha256(et_email.getText().toString().replace(" ",""));

        String cryptohash = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

        //Campos vacios
        if (et_email.getText().toString().equals("") && et_pwd.getText().toString().equals("")){
            et_email.setError("");
            et_email.requestFocus();

            et_pwd.setError("Revisar campos");
            et_pwd.requestFocus();
        } else {
            try{
                //Enviamos el paquete JSON
                DataClass.usr = new User(et_email.getText().toString().replace(" ",""), cryptohash);
                new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=login&json=" + DataClass.usr.toJsonL()).get();
            }catch(Exception e){
                //Problemas de conexón
                Toast.makeText(this, "[Error] General", Toast.LENGTH_LONG).show();
            }
            //Retorno del PHP
            if (DataClass.UserJson.equals("0") || DataClass.UserJson.equals("1") || DataClass.UserJson.equals("")) {
                ShowProgress();
                Toast.makeText(this, "[Error] Email o Contraseña", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            } else {
                //En caso correcto nos logeamos
                ShowProgress();
                DataClass.GlobalUser = User.GetObj(DataClass.UserJson);
                Intent Intent = new Intent(this,  WelcomeActivity.class);
                startActivity(Intent);
                finish();
            }
        }
    }

    //Hacer pruebas
    public void onlyDEV(View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
        startActivity(Intent);
        finish();
    }
}
