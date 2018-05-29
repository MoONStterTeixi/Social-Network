package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.System.exit;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_username, tv_checkID;
    private ImageView iv_image;
    private Button b_email, b_pass, b_genre;
    private Spinner s_profile_selcgen;

    private boolean unlock = false;
    int value = 0;
    private String passwordFinal = "";

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        myDialog = new Dialog(this);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Reference();

        if (DataClass.GlobalUser == null) {
            tv_username.setText("TestDummy");
        } else {
            tv_username.setText(DataClass.GlobalUser.getUsername());
        }
        iv_image.setBackgroundResource(R.drawable.profile_lock);

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

    public void Reference() {
        tv_username = findViewById(R.id.tvemail_profile);
        iv_image = findViewById(R.id.profile_image);
        b_email = findViewById(R.id.profile_email);
        b_pass = findViewById(R.id.profile_pass);
        b_genre = findViewById(R.id.profile_genre);
        tv_checkID = findViewById(R.id.checkID);
    }

    public void ShowEmail(View v) {
            if (!unlock){
                Toast.makeText(this, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
            }else{
                TextView txtclose;
                final EditText ce_email;
                Button btnFollow;

                myDialog.setContentView(R.layout.profile_change_email);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("");

                ce_email = (EditText) myDialog.findViewById(R.id.ce_email);
                ce_email.setHint(DataClass.GlobalUser.getEmail());

                btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
                btnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pwdFinal = CryptoHash.getSha256(passwordFinal);
                        String userFinal = CryptoHash.getSha256(ce_email.getText().toString().replace(" ",""));

                        //Formula final de la encryptación
                        String cryptohashFinal = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

                        try{
                            DataClass.usr = new User(DataClass.GlobalUser.getUsername(),ce_email.getText().toString().replace(" ",""), null,0,true);
                            new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                            //Toast.makeText(ProfileActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                            if (DataClass.UserJson.equals("1")){
                                Toast.makeText(ProfileActivity.this, "Email cambiado con exito", Toast.LENGTH_LONG).show();
                                DataClass.profileFAIL = 1;
                                myDialog.dismiss();
                            }else{
                                Toast.makeText(ProfileActivity.this, "Error al intentar cambiar el email", Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception e){

                        }
                    }
                });

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }


    }

    public void ShowPass(View v) {
        if (!unlock){
            Toast.makeText(this, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
        }else{
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.profile_change_pass);
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
    }

    public void ShowGenre(View v) {
        if (!unlock){
            //getBaseContext()
            Toast.makeText(this, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
        }else{
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.profile_change_genre);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");

            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            Spinner spinner = (Spinner) myDialog.findViewById(R.id.profile_selcgen);
            String[] valores = {"Mujer","Hombre","Otro"};
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                {
                    /*int fin = (int) adapterView.getItemAtPosition(position);
                    Toast.makeText(adapterView.getContext(), "Elegido" + fin, Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                    // vacio

                }
            });

            btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        DataClass.usr = new User(DataClass.GlobalUser.getUsername(),0,true);
                        new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJsonCG()).get();

                        //Toast.makeText(ProfileActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                        if (DataClass.UserJson.equals("1")){
                            Toast.makeText(ProfileActivity.this, "Email cambiado con exito", Toast.LENGTH_LONG).show();
                            DataClass.profileFAIL = 1;
                            myDialog.dismiss();
                        }else{
                            Toast.makeText(ProfileActivity.this, "Error al intentar cambiar el email", Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){

                    }
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }

    public void ShowOKPASS(View v) {
        DataClass.contadoID = DataClass.contadoID - 1;
        if (!unlock){
            final TextView txtclose, pass;

            myDialog.setContentView(R.layout.profile_vchange_email);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");

            pass = myDialog.findViewById(R.id.passCheck);

            passwordFinal = pass.getText().toString().replace(" ","");

            Button btnFollow;
            btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String userFinal = CryptoHash.getSha256(DataClass.GlobalUser.getEmail());
                    String pwdFinal = CryptoHash.getSha256(pass.getText().toString().replace(" ",""));

                    String cryptohash =  CryptoHash.getSha256(pwdFinal +"."+ userFinal);
                    if (cryptohash.equals(DataClass.cryptohash)){
                        myDialog.dismiss();
                        iv_image.setBackgroundResource(R.drawable.profile_open);
                        b_email.setBackgroundColor(0xFF1FA441);
                        b_pass.setBackgroundColor(0xFF1FA441);
                        b_genre.setBackgroundColor(0xFF1FA441);
                        DataClass.contadoID = 3;

                        tv_checkID.setBackgroundColor(Color.TRANSPARENT);
                        tv_checkID.setText(null);
                        unlock = true;
                    }else{
                        tv_checkID.setBackgroundColor(Color.RED);
                        myDialog.dismiss();
                        if (DataClass.contadoID >=1){
                            tv_checkID.setText("[Tienes "+DataClass.contadoID+" intentos] Contraseña erronea");
                        }

                        if(DataClass.contadoID == 0){
                            DataClass.profileFAIL = 1;

                            tv_checkID.setText("La sesión se cerrara en breves...");
                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ProfileActivity.this);
                            myBuild.setMessage("Cerrando sesión por seguridad");
                            myBuild.setTitle("Epidemic Games");

                            DataClass.info = "Vuelve a escribir las credenciales";
                            DataClass.color = 1;

                            myBuild.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog dialog = myBuild.create();
                            dialog.show();
                            thread(3000);
                        }
                    }
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }else{
            Toast.makeText(getBaseContext(), "Se cierra al salir del perfil :)", Toast.LENGTH_LONG).show();
        }
    }

    private void thread(final int time) {
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(time);
                } catch (Exception e) {

                } finally {
                    if (value == 0){
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        };
        welcomeThread.start();
    }

}
