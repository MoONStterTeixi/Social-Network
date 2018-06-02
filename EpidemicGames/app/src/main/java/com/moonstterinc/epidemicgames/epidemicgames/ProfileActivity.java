package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;

import static java.lang.System.exit;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_username, tv_checkID, pass;
    private ImageView iv_image,image_profile, profile_trans;
    private Button b_email, b_pass, b_genre;
    private Spinner s_profile_selcgen;

    private boolean unlock = false;
    int value = 0;
    int posResult;

    Dialog myDialog;


    Bitmap bmap;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IMAGE = "image";
    private String image;

    public static final String PROFILE = "profile";
    private int profile = 0;

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

        loadData();
        updateViews();

        if (profile == 1) {
            profile_trans.setBackgroundColor(Color.TRANSPARENT);
        }
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
        image_profile = findViewById(R.id.profile_you);
        profile_trans = findViewById(R.id.profile_trans);
    }

    public void onClick (View v){
        cargarImagen();
        // Getting drawable image via drawable-hdpi folder and covert into bitmap.
    }


    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            image_profile.setImageURI(path);

            image_profile.buildDrawingCache();
            bmap = image_profile.getDrawingCache();
            profile = 1;
            image_profile.setBackgroundColor(Color.TRANSPARENT);
            Toast.makeText(this, "Foto de perfil guardada", Toast.LENGTH_LONG).show();
            saveData();
        }

    }

    //Guardar Datos
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
        byte [] b=ByteStream.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        editor.putString(IMAGE, temp);
        editor.putInt(PROFILE, profile);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        image = sharedPreferences.getString(IMAGE,"");
        profile = sharedPreferences.getInt(PROFILE,  profile);
    }

    public void updateViews(){
        profile =+ profile;
        byte [] encodeByte=Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        image_profile.setImageBitmap(bitmap);
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
                        String pwdFinal = CryptoHash.getSha256(pass.getText().toString().replace(" ",""));
                        String userFinal = CryptoHash.getSha256(ce_email.getText().toString().replace(" ",""));

                        //Formula final de la encryptación
                        String cryptohashFinal = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

                        try{
                            DataClass.usr = new User(DataClass.GlobalUser.getUsername(),ce_email.getText().toString().replace(" ",""), cryptohashFinal, DataClass.GlobalUser.getGenre(),true);
                            new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                            //Toast.makeText(ProfileActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();
                        if(!DataClass.GlobalUser.getEmail().equals(userFinal)){
                            tv_checkID.setBackgroundColor(Color.BLACK);
                            tv_checkID.setText("[Email actual] Anulado opración.");
                            myDialog.dismiss();
                        }else{
                            if (DataClass.UserJson.equals("1")){
                                tv_checkID.setBackgroundColor(Color.BLACK);
                                tv_checkID.setText("Cerrando y aplicando: Gracias, Adiós.");

                                DataClass.profileFAIL = 1;
                                myDialog.dismiss();

                                AlertDialog.Builder myBuild = new AlertDialog.Builder(ProfileActivity.this);
                                myBuild.setMessage("Email cambiado con exito.\nCerrando sesión para aplicar cambios...");
                                myBuild.setTitle("Epidemic Games");

                                DataClass.info = "Vuelve a escribir las credenciales.";
                                DataClass.color = 2;

                                myBuild.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog dialog = myBuild.create();
                                dialog.show();
                                thread(3000);
                            }else{
                                tv_checkID.setBackgroundColor(Color.YELLOW);
                                tv_checkID.setTextColor(Color.BLACK);
                                tv_checkID.setText("Email ya registrado :(");
                                Toast.makeText(ProfileActivity.this, "Error al intentar cambiar el email", Toast.LENGTH_LONG).show();
                                myDialog.dismiss();
                            }
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
            final EditText new_pass;

            Button btnFollow;
            myDialog.setContentView(R.layout.profile_change_pass);

            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");

            new_pass = (EditText) myDialog.findViewById(R.id.new_pass);

            btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pwdFinal = CryptoHash.getSha256(new_pass.getText().toString().replace(" ",""));
                    String userFinal = CryptoHash.getSha256(DataClass.GlobalUser.getEmail());

                    //Formula final de la encryptación
                    String cryptohashFinal = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

                    try{
                        DataClass.usr = new User(DataClass.GlobalUser.getUsername(),DataClass.GlobalUser.getEmail(), cryptohashFinal, DataClass.GlobalUser.getGenre(),true);
                        new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                        //Toast.makeText(ProfileActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                        if (DataClass.UserJson.equals("1")){
                            //Toast.makeText(ProfileActivity.this, "Password cambiado con exito", Toast.LENGTH_LONG).show();

                            tv_checkID.setBackgroundColor(Color.BLACK);
                            tv_checkID.setText("Cerrando y aplicando: Gracias, Adiós");

                            DataClass.profileFAIL = 1;
                            myDialog.dismiss();

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ProfileActivity.this);
                            myBuild.setMessage("Contraseña cambiada con exito.\nCerrando sesión para aplicar cambios...");
                            myBuild.setTitle("Epidemic Games");

                            DataClass.info = "Vuelve a escribir las credenciales.";
                            DataClass.color = 2;

                            myBuild.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog dialog = myBuild.create();
                            dialog.show();
                            thread(3000);
                        }else{

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
            String[] valores = {"Elige género:","Mujer","Hombre","Otro"};
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                {
                    //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                    int pos = position;
                    posResult = pos;
                    //Toast.makeText(ProfileActivity.this, ""+pos, Toast.LENGTH_LONG).show();
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
                    String pwdFinal = CryptoHash.getSha256( pass.getText().toString().replace(" ",""));
                    String userFinal = CryptoHash.getSha256(DataClass.GlobalUser.getEmail());

                    //Formula final de la encryptación
                    String cryptohashFinal = CryptoHash.getSha256(pwdFinal +"."+ userFinal);

                    if (posResult == 0 ){
                        Toast.makeText(ProfileActivity.this, "Elige tú género...", Toast.LENGTH_LONG).show();
                    }else{
                        try{
                            DataClass.usr = new User(DataClass.GlobalUser.getUsername(),DataClass.GlobalUser.getEmail(),cryptohashFinal, posResult,true);
                            new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                            //Toast.makeText(ProfileActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                            if (DataClass.UserJson.equals("1")){
                                //Toast.makeText(ProfileActivity.this, "Password cambiado con exito", Toast.LENGTH_LONG).show();
                                tv_checkID.setBackgroundColor(Color.BLACK);
                                tv_checkID.setText("Genéro aplicado: Gracias, Adiós");
                                myDialog.dismiss();
                            }else{

                            }
                        }catch(Exception e){

                        }
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
            final TextView txtclose;

            myDialog.setContentView(R.layout.profile_vchange_email);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");

            pass = myDialog.findViewById(R.id.passCheck);

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
                        finish();
                    }
                }
            }
        };
        welcomeThread.start();
    }

}
