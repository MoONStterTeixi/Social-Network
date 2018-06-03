package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private TextView set_result;
    private Switch s_card, s_notice;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCHCARD = "switchcard";
    public static final String SWITCHNEWS = "switchnews";

    private boolean switchOnOffCard;
    private boolean switchOnOffNews;

    //Variebles en comun
    public static final String NOMORE = "nomore";
    private int nomore = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Reference();

        loadData();
        updateViews();

        if(nomore == 1){
            s_card.setChecked(false);
        }

        s_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    nomore = 0;
                    Toast.makeText(getBaseContext(), "Notificación Card Restablecida", Toast.LENGTH_LONG).show();
                    set_result.setBackgroundColor(Color.YELLOW);
                    set_result.setTextColor(Color.BLACK);
                    set_result.setText("La Notificación Card saldrá en el siguiente inicio.");
                    saveData();
                }
            }
        });

        s_notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    try{
                        DataClass.usr = new User(DataClass.GlobalUser.getUsername(),DataClass.GlobalUser.getEmail(), DataClass.GlobalUser.getPassword(), DataClass.GlobalUser.getGenre(),true);
                        new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                        //Toast.makeText(SettingsActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                        if (DataClass.UserJson.equals("1")){
                            Toast.makeText(SettingsActivity.this, "Subscrito al noticiario", Toast.LENGTH_LONG).show();
                        }
                        saveData();
                    }catch(Exception e){

                    }
                }else{
                    try{
                        DataClass.usr = new User(DataClass.GlobalUser.getUsername(),DataClass.GlobalUser.getEmail(), DataClass.GlobalUser.getPassword(), DataClass.GlobalUser.getGenre(),false);
                        new CallAPI_Rest().execute("http://www.moonstterinc.com/SN/query.php?action=update&json="+DataClass.usr.toJson()).get();

                        //Toast.makeText(SettingsActivity.this, "PHP dice:"+DataClass.UserJson, Toast.LENGTH_LONG).show();

                        if (DataClass.UserJson.equals("1")){
                            Toast.makeText(SettingsActivity.this, "Desuscrito al noticiario", Toast.LENGTH_LONG).show();
                        }
                        saveData();
                    }catch(Exception e){

                    }
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

    public void Reference(){
        s_card = findViewById(R.id.switchCard);
        set_result = findViewById(R.id.set_result);
        s_notice = findViewById(R.id.switchNotice);
    }

    //Prueba
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SWITCHCARD, s_card.isChecked());
        editor.putBoolean(SWITCHNEWS, s_notice.isChecked());
        editor.putInt(NOMORE, nomore);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        switchOnOffCard = sharedPreferences.getBoolean(SWITCHCARD, false);
        switchOnOffNews= sharedPreferences.getBoolean(SWITCHNEWS, false);
        nomore = sharedPreferences.getInt(NOMORE, nomore);
    }

    public void updateViews(){
        s_card.setChecked(switchOnOffCard);
        s_notice.setChecked(switchOnOffNews);
        nomore =+ nomore;
    }
}
