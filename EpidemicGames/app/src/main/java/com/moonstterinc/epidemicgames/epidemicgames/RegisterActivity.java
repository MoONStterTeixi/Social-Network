package com.moonstterinc.epidemicgames.epidemicgames;

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

    public void terms(View v){
       android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(this);
        myBuild.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed turpis ex, feugiat at dolor quis," +
                " elementum rutrum lorem. Morbi viverra leo eget scelerisque imperdiet. Nulla sit amet commodo enim.\n" +
                " Sed congue sed diam sit amet pharetra. Vestibulum vitae nulla eu metus varius venenatis ac nec urna.\n\n\n\n" +
                " Quisque ut lorem tempus orci porttitor porttitor nec in mauris. Ut sem velit, semper et enim viverra, " +
                "porttitor posuere ligula. Sed tincidunt, risus et bibendum lobortis, nunc nisl pellentesque quam, in vulputate felis tortor ac justo.\n\n\n" +
                " In ornare ex vitae eros ornare commodo. Vestibulum porttitor, felis in hendrerit porttitor, ligula quam tincidunt mi," +
                " eu condimentum urna neque ac eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                "Sed et tortor mollis, convallis mauris nec, pellentesque eros."+
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed turpis ex, feugiat at dolor quis," +
                " elementum rutrum lorem. Morbi viverra leo eget scelerisque imperdiet. Nulla sit amet commodo enim.\n" +
                " Sed congue sed diam sit amet pharetra. Vestibulum vitae nulla eu metus varius venenatis ac nec urna.\n\n\n\n" +
                " Quisque ut lorem tempus orci porttitor porttitor nec in mauris. Ut sem velit, semper et enim viverra, " +
                "porttitor posuere ligula. Sed tincidunt, risus et bibendum lobortis, nunc nisl pellentesque quam, in vulputate felis tortor ac justo.\n\n\n" +
                " In ornare ex vitae eros ornare commodo. Vestibulum porttitor, felis in hendrerit porttitor, ligula quam tincidunt mi," +
                " eu condimentum urna neque ac eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                "Sed et tortor mollis, convallis mauris nec, pellentesque eros.");

        myBuild.setTitle("Epidemic Games Info");

        myBuild.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cb_accept.setChecked(true);
            }
        });
        myBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

       int a =  et_username.getText().length();
       if (cb_accept.isChecked()) {
            if( a < 3){
                et_username.setError("Invalid Username:\n" +
                        "-is at least 4 character long");
                et_username.requestFocus();
            }else if(!validateEmail(et_email.getText().toString())) {
               et_email.setError("Invalid Email");
               et_email.requestFocus();

            } else if (!isValid(et_pwd.getText().toString())) {
               et_pwd.setError("Invalid Password:\n" +
                       "-contains at least 1 digit\n" +
                       "-contains at least 1 lowercase letter\n" +
                       "-contains at least 1 lowercase letter\n" +
                       "-contains at least 1 of the special\n" +
                       "-is at least 6 character long\n" +
                       "-is at most 15 characters long");
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
       }else{
           Toast.makeText(this, "Read conditions and then Accept", Toast.LENGTH_LONG).show();
       }
    }
}
