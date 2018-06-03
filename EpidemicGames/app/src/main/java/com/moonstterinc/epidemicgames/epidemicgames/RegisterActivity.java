package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_email ,et_pwd, et_repwd;
    private CheckBox cb_acceptTerm,cb_accept;
    private TextView tv_noConn, tv_register_err_username, tv_register_err_email;
    int resultRG;

    private ProgressDialog progressDialog;

    //Patrones de password
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

    //private RadioGroup rggenre;
    //private RadioButton rbother, rbfemale, rbmale;
    //Instaciamos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Reference();

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


    public void Reference(){
        et_username = findViewById(R.id.username);
        et_email = findViewById(R.id.email);
        et_pwd = findViewById(R.id.password);
        et_repwd = findViewById(R.id.repPassword);
        tv_noConn = findViewById(R.id.noConn);
        cb_acceptTerm = findViewById(R.id.acceptTerm);
        cb_accept = findViewById(R.id.accept);
        tv_register_err_username  = findViewById(R.id.register_err_username);
        tv_register_err_email = findViewById(R.id.register_err_email);

        //rggenre = findViewById(R.id.genre);
        /*rbother = findViewById(R.id.other);
        rbfemale = findViewById(R.id.female);
        rbmale = findViewById(R.id.male);*/
    }
    //Selecionar el Grupo de radio
   /* public void selectRadioGroup() {
        int radioButtonID = rggenre.getCheckedRadioButtonId();
        View radioButton = rggenre.findViewById(radioButtonID);
        resultRG = rggenre.indexOfChild(radioButton);
    }*/

    //Return true si el correo es valido de lo contrario será false
    protected boolean validateEmail(String email) {
        String emailPattern = DataClass.pattern;
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    //Verifica si los patrones de password son los acordado
    public boolean isValid(String password){
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //Tarjeta de terminos
    public void terms(View v){
        android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(this);
        myBuild.setMessage("Sin informacion");

        myBuild.setTitle("[Info] Epidemic Games");

        myBuild.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cb_acceptTerm.setChecked(true);
            }
        });
        myBuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cb_acceptTerm.setChecked(false);
            }
        });

        android.app.AlertDialog dialog = myBuild.create();
        dialog.show();
    }

    //Crear la tarjeta del progresso
    public void ShowProgress(){
        //agregas un mensaje en el ProgressDialog
        progressDialog.setTitle("Validando usuario");
        progressDialog.setMessage("Puede tardar un poquito..");
        //muestras el ProgressDialog
        progressDialog.show();
    }

    //Boton registro
    public void goLogin (View v) throws InterruptedException {
        //selectRadioGroup();

        //Encryptar password con Email y password
        String pwdFinal = CryptoHash.getSha256(et_pwd.getText().toString().replace(" ",""));
        String userFinal = CryptoHash.getSha256(et_email.getText().toString().replace(" ",""));

        //Formula final de la encryptación
        String cryptohash = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

       //Obtenemos la logitud
       int a =  et_username.getText().length();

        //Username menos de 4
        if( a < 4){
            et_username.setError("Usuario inválido:\n" +
                    "-Mínimo 4 caracteres.");
            //Error en modo foco
            et_username.requestFocus();


            //Correo no válido
        }if(!validateEmail(et_email.getText().toString())) {
            et_email.setError("Correo inválido.");
            et_email.requestFocus();


        }

        //Contraseña no válida
        if (!isValid(et_pwd.getText().toString())) {
            et_pwd.setError("Contraseña inválida:\n" +
                    "-Mínimo 1 Número,\n" +
                    "-Mínimo 1 una letra MAYÚSCULA,\n" +
                    "-Mínimo 1 una letra minúscula,\n" +
                    "-Contener 1 Carácter especial\n                 |Mama mío! :) |,\n" +
                    "-Debe ser mínimo de 6 Carácteres,\n" +
                    "-Máximo de 15 Carácteres.");
            et_pwd.requestFocus();
        }

        //Contraseña no iguales
        if (!et_pwd.getText().toString().equals(et_repwd.getText().toString())) {
            //Toast.makeText(this, "Contraseña: ¡No son iguales!", Toast.LENGTH_LONG).show();
            et_pwd.setError("Contraseña: ¡No son iguales!");
            et_pwd.requestFocus();

            et_repwd.setError("Contraseña: ¡No son iguales!");
            et_repwd.requestFocus();
        }

       //Verficamos que el check de terminos
       if (cb_acceptTerm.isChecked()) {
           try{
               DataClass.usr = new User(et_username.getText().toString().replace(" ",""), et_email.getText().toString().replace(" ",""), cryptohash,resultRG ,cb_accept.isChecked());
               //new CallAPI_Rest().execute("http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=register&json="+usr.toJson());
               new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=register&json="+DataClass.usr.toJson()).get();

               //Toast.makeText(this, DataClass.UserJson, Toast.LENGTH_LONG).show();

               //Retorno del PHP
               if (DataClass.UserJson.equals("2")){
                   //Toast.makeText(this, "[Error] Usuario ya en uso", Toast.LENGTH_LONG).show();
                   tv_register_err_username.setBackgroundColor(Color.RED);
                   tv_register_err_email.setBackgroundColor(Color.TRANSPARENT);

               }else if (DataClass.UserJson.equals("1")){
                   tv_register_err_username.setBackgroundColor(Color.TRANSPARENT);
                   tv_register_err_email.setBackgroundColor(Color.RED);
                   //Toast.makeText(this, "[Error] Email ya registrado", Toast.LENGTH_LONG).show();

               }else if (DataClass.UserJson.equals("3")){
                   tv_register_err_username.setBackgroundColor(Color.RED);
                   tv_register_err_email.setBackgroundColor(Color.RED);
                  // Toast.makeText(this, "[Error] Usuario y Email existentes", Toast.LENGTH_LONG).show();

               }else{
                   tv_register_err_username.setBackgroundColor(Color.TRANSPARENT);
                   tv_register_err_email.setBackgroundColor(Color.TRANSPARENT);
                   ShowProgress();
                   //Vamos a Login
                   Intent Intent = new Intent(this, LoginActivity.class);
                   startActivity(Intent);
                   finish();
               }
           }catch(Exception e){
               Log.d("tag", String.valueOf(e));
               //Problemas de conexón
               tv_noConn.setBackgroundColor(Color.RED);
               tv_noConn.setText("Revisa la conexión");
           }
       }else{
           Toast.makeText(this, "Lee los Términos y Condiciones \n     (Si esta conforme, acepte)", Toast.LENGTH_LONG).show();
       }
    }
}
