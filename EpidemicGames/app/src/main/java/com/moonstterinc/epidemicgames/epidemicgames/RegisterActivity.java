package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
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

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.moonstterinc.epidemicgames.epidemicgames.DataClass.context;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_email ,et_pwd, et_repwd;
    //private RadioGroup rggenre;
    private RadioButton rbother, rbfemale, rbmale;
    private CheckBox cb_accept;

    int resultRG;
    String msg = null;

    //Instaciamos
    private ProgressDialog progressDialog;

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

    public void terms(View v){
       android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(this);
        myBuild.setMessage("Sin informacion");

        myBuild.setTitle("[Info] Epidemic Games");

        myBuild.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cb_accept.setChecked(true);
            }
        });
        myBuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cb_accept.setChecked(false);
            }
        });

        android.app.AlertDialog dialog = myBuild.create();
        dialog.show();
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
        String pwdFinal = CryptoHash.getSha256(et_pwd.getText().toString().replace(" ",""));
        String userFinal = CryptoHash.getSha256(et_email.getText().toString().replace(" ",""));

        String cryptohash = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

       int a =  et_username.getText().length();
       if (cb_accept.isChecked()) {
            if( a < 4){
                et_username.setError("Usuario inválido:\n" +
                        "-Mínimo 4 caracteres.");
                et_username.requestFocus();
            }else if(!validateEmail(et_email.getText().toString())) {
               et_email.setError("Correo inválido.");
               et_email.requestFocus();

            } else if (!isValid(et_pwd.getText().toString())) {
               et_pwd.setError("Contraseña inválida:\n" +
                       "-Mínimo 1 Número,\n" +
                       "-Mínimo 1 una letra MAYÚSCULA,\n" +
                       "-Mínimo 1 una letra minúscula,\n" +
                       "-Contener 1 Carácter especial\n                 |Mama mío! :) |,\n" +
                       "-Debe ser mínimo de 6 Carácteres,\n" +
                       "-Máximo de 15 Carácteres.");
               et_pwd.requestFocus();

            } else if (et_pwd.getText().toString().equals(et_repwd.getText().toString())){
               User usr = new User(et_username.getText().toString().replace(" ",""), et_email.getText().toString().replace(" ",""), cryptohash,resultRG ,cb_accept.isChecked());
               //new CallAPI_Rest().execute("http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=register&json="+usr.toJson());
               new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=register&json="+usr.toJson());

                //agregas un mensaje en el ProgressDialog
                progressDialog.setTitle("Validando registro");
                progressDialog.setMessage("Esto puede tardar un poquito...");
                //muestras el ProgressDialog
                progressDialog.show();
               //Toast.makeText(this, "Crypt:" + cryptohash, Toast.LENGTH_LONG).show();
               Intent Intent = new Intent(this, LoginActivity.class);
               startActivity(Intent);
               finish();

            }else{
               //Toast.makeText(this, "Contraseña: ¡No son iguales!", Toast.LENGTH_LONG).show();
                et_pwd.setError("Contraseña: ¡No son iguales!");
                et_pwd.requestFocus();

               et_repwd.setError("Contraseña: ¡No son iguales!");
               et_repwd.requestFocus();
            }
       }else{
           Toast.makeText(this, "Lee los Términos y Condiciones \n     (Si esta conforme, acepte)", Toast.LENGTH_LONG).show();
       }
    }
}
