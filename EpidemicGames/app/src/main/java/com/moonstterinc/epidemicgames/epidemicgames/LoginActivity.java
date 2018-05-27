package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
    private ToggleButton tb_pwd;
    private TextView tv_noConn;
    private Switch s_saveLogin;

    //Instaciamos el progreso
    private ProgressDialog progressDialog;

    //Guardar Credenciales
    Dialog myDialog;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "email";
    public static final String PWD = "pwd";
    public static final String SWITCH = "switch";

    public static final String DETAILS = "datails";

    private String email;
    private String pwd;
    private boolean switchOnOff;
    private int details = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDialog = new Dialog(this);

        Reference();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Switch si es check hace una cosa sino otra
        s_saveLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (details == 0){
                        Toast.makeText(getBaseContext(), "¡Credenciales guardadas!", Toast.LENGTH_LONG).show();
                        details = 1;
                    }
                    saveData();
                }
                else {
                    et_email.setText(null);
                    et_pwd.setText(null);
                    s_saveLogin.setChecked(false);

                    if(details == 1){
                        Toast.makeText(getBaseContext(), "¡Credenciales eliminas!", Toast.LENGTH_LONG).show();
                        details =+ 0;
                    }
                    saveData();
                }
            }
        });

        //Cargamos datos (presistencia)
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

    //Las referencias
    private void Reference(){
        et_email = findViewById(R.id.email);
        et_pwd =  findViewById(R.id.pwd);
        s_saveLogin = findViewById(R.id.saveLogin);
        tb_pwd = findViewById(R.id.toggleButton);
        tv_noConn = findViewById(R.id.noConn);
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
        editor.putInt(DETAILS, details);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL,"");
        pwd = sharedPreferences.getString(PWD, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH, false);
        details = sharedPreferences.getInt(DETAILS, details);
    }

    public void updateViews(){
        et_email.setText(email);
        et_pwd.setText(pwd);
        s_saveLogin.setChecked(switchOnOff);
        details =+ details;
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

    //Botton login
    public void goWelcome (View v){

        //Encryptar password con Email y password
        String pwdFinal = CryptoHash.getSha256(et_pwd.getText().toString().replace(" ",""));
        String userFinal = CryptoHash.getSha256(et_email.getText().toString().replace(" ",""));

        //Formula final de la encryptación
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

                //Retorno del PHP
                if (DataClass.UserJson.equals("0") || DataClass.UserJson.equals("1")) {
                    ShowProgress();
                    Toast.makeText(this, "[Error] Email o Contraseña", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (DataClass.UserJson.equals("")){
                    //Problemas de conexón
                    tv_noConn.setBackgroundColor(Color.RED);
                    tv_noConn.setText("Revisa la conexión");
                } else {
                    //En caso correcto nos logeamos
                    ShowProgress();
                    DataClass.GlobalUser = User.GetObj(DataClass.UserJson);
                    Intent Intent = new Intent(this,  WelcomeActivity.class);
                    startActivity(Intent);
                    finish();
                }
            }catch(Exception e){

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
