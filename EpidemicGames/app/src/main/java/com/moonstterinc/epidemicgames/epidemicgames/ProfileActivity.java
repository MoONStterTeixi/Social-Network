package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.moonstterinc.epidemicgames.epidemicgames.DataClass.context;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_username;
    private ImageView iv_image;
    private Button b_email, b_pass, b_genre;

    private boolean unlock = false;

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

        //b_email.setEnabled(false);
        //b_pass.setEnabled(false);
        //b_genre.setEnabled(false);
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
    }

    public void ShowPass(View v) {
        if (!unlock){
            Toast.makeText(context, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
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

    public void ShowEmail(View v) {
        if (!unlock){
            Toast.makeText(context, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
        }else{
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.profile_change_email);
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
            Toast.makeText(context, "Antes debes desbloquear el candado", Toast.LENGTH_LONG).show();
        }else{
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.profile_change_genre);
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

    public void ShowOKPASS(View v) {

        if (!unlock){
            final TextView txtclose, pass;

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
                    String a = "a";
                    if (pass.getText().toString().equals("a")){
                        myDialog.dismiss();
                        iv_image.setBackgroundResource(R.drawable.profile_open);
                        unlock = true;
                        //b_email.setEnabled(true);
                        //b_pass.setEnabled(true);
                        //b_genre.setEnabled(true);

                    }else{
                        myDialog.dismiss();
                        Snackbar.make(view, "Contraseña erronea", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }else{
            Toast.makeText(context, "Se cierra al salir del perfil :)", Toast.LENGTH_LONG).show();
        }

    }

}
