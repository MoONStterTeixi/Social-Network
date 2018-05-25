package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
   // private TextView tx_login;
    private Switch s_saveLogin;
    //static final int READ_BLOCK_SIZE = 100;
    Dialog myDialog;

    //Instaciamos
    private ProgressDialog progressDialog;

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

        //onClickLoading();
        et_email.setText(DataClass.sSubCadena1);
        et_pwd.setText(DataClass.sSubCadena2);
        if (DataClass.resultLogin == true){
            s_saveLogin.setChecked(true);
        }else{
            et_email.setText(null);
            et_pwd.setText(null);
            s_saveLogin.setChecked(false);

        }

        s_saveLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    DataClass.saveLogin = "YES";
                    //tx_login.setText("Check");
                    //onClickSave();
                }
                else {
                    DataClass.saveLogin = "NO";
                    //tx_login.setText("NOCheck");
                    //onClickSave();
                }
            }
        });

        final CheckBox showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);
        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPasswordCheckBox.isChecked()){
                    et_pwd.setTransformationMethod(null);
                }else{
                    et_pwd.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        //Inicializamos la barra
        progressDialog= new ProgressDialog(this);
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
        s_saveLogin = findViewById(R.id.saveLogin);
    }

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

    public void goWelcome (View v){
        //Encryptar password con Email y password
        String pwdFinal = CryptoHash.getSha256(et_pwd.getText().toString().replace(" ",""));
        String userFinal = CryptoHash.getSha256(et_email.getText().toString().replace(" ",""));

        String cryptohash = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

        //Toast.makeText(this,et_email.getText() , Toast.LENGTH_LONG).show();

        //ERRORx0E00 Campos vacios
        if (et_email.getText().toString().equals("") && et_pwd.getText().toString().equals("")){
            et_email.setError("");
            et_email.requestFocus();

            et_pwd.setError("Revisar campos");
            et_pwd.requestFocus();
        }

        //ERRORx0EO01 Correo invalido
        if (!validateEmail(et_email.getText().toString())) {
            et_email.setError("Revisar campos");
            et_email.requestFocus();;
        //ERRORx0ED01 Password invalido
        }else if (!isValid(et_pwd.getText().toString())) {
            et_pwd.setError("Revisar campos");
            et_pwd.requestFocus();
        } else {
            try{
                DataClass.usr = new User(et_email.getText().toString().replace(" ",""), cryptohash);
                //localhost= new CallAPI_Rest().execute("http://172.17.129.67:80/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json=" + DataClass.usr.toJsonL()).get();
                new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=login&json=" + DataClass.usr.toJsonL()).get();
                //tx_login.setText(DataClass.UserJson);
            }catch(Exception e){
                //ERRORx00E0 Problemas con el server
                Toast.makeText(this, "[ERRORx00E0] General", Toast.LENGTH_LONG).show();
            }
            //[ERRORx00E2 Correo o pass fake
            if (DataClass.UserJson.equals("0") || DataClass.UserJson.equals("1") || DataClass.UserJson.equals("")) {
                Toast.makeText(this, "[ERRORx00E2] Email o Contraseña", Toast.LENGTH_LONG).show();
            } else {
                //agregas un mensaje en el ProgressDialog
                progressDialog.setTitle("Validando usuario");
                progressDialog.setMessage("Iniciado sesión");
                //muestras el ProgressDialog
                progressDialog.show();

                DataClass.GlobalUser = User.GetObj(DataClass.UserJson);
                //Toast.makeText(this, "Input Validation Success", Toast.LENGTH_LONG).show();
                /*Intent Intent = new Intent(this, WelcomeActivity.class);
                startActivity(Intent);
                finish();*/

                Intent Intent = new Intent(this,  Welcomev2Activity.class);
                startActivity(Intent);
                finish();
            }
        }

    }

    public void onlyDEV(View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
        startActivity(Intent);
        finish();
    }

    public void onlyDEV2(View v){
        Intent Intent = new Intent(this, Welcomev2Activity.class);
        startActivity(Intent);
        finish();
    }

    /*public void contador (String c){
        for(int x=0;x<c.length();x++) {
            contador++;
        }
        tx_login.setText("S: "+contador);
    }*/

    //Guardar datos y Cargar en TXT
    /*public void onClickSave(){
        String str =  "PEPE";
        String str2 =  "PEPE";

        try{
            //FileOutputStream fos = openFileOutput("textFile.txt", MODE_PRIVATE);
            FileOutputStream fos = openFileOutput("rememberME.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            // Escribimos el String en el archivo
            osw.write(DataClass.saveLogin + str + str2);
            osw.flush();
            osw.close();

            // Mostramos que se ha guardado
            Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT).show();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }*/


    /*public void onClickLoading(){
        try{
            FileInputStream fis = openFileInput("rememberME.txt");
            InputStreamReader isr = new InputStreamReader(fis);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";

            int charRead;
            while((charRead = isr.read(inputBuffer)) > 0){
                // Convertimos los char a String
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            String sSubCadena = "";
            try{
                sSubCadena = s.substring(0,3);
                DataClass.sSubCadena1 = s.substring(3,7);
                DataClass.sSubCadena2 = s.substring(7,11);
            }catch (Exception e){

            }
            if(sSubCadena.equals("YES")){
                DataClass.resultLogin = true;
            }

            // Mostramos un Toast con el proceso completado
            Toast.makeText(getBaseContext(), "Cargado", Toast.LENGTH_SHORT).show();

            isr.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }*/
}
