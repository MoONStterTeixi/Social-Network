package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_pwd;
    private TextView tx_login;
    private Switch s_saveLogin;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Reference();
        DataClass.context = this.getApplicationContext();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onClickLoading();

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
                    onClickSave();
                }
                else {
                    DataClass.saveLogin = "NO";
                    //tx_login.setText("NOCheck");
                    onClickSave();
                }
            }
        });


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
        tx_login = findViewById(R.id.login);
        s_saveLogin = findViewById(R.id.saveLogin);

    }

    public void goWelcome (View v){
        try{
            DataClass.usr = new User(et_email.getText().toString(), et_pwd.getText().toString());
            new CallAPI_Rest().execute("http://172.17.129.63:80/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=login&json=" + DataClass.usr.toJsonL()).get();
            tx_login.setText(DataClass.UserJson);
        }catch(Exception e){
            Toast.makeText(this, "[ERROR] User o Password", Toast.LENGTH_LONG).show();
        }
        if (DataClass.UserJson.equals("0") || DataClass.UserJson.equals("1") || DataClass.UserJson.equals("")) {
            Toast.makeText(this, "[ERROR] Turn new", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent Intent = new Intent(this, WelcomeActivity.class);
            startActivity(Intent);
            finish();
        }
    }

    public void onlyDEV(View v){
        Intent Intent = new Intent(this, WelcomeActivity.class);
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
    public void onClickSave(){
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

    }


    public void onClickLoading(){
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
    }
}
