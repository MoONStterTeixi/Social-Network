package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.telecom.Call;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_statusTime, tv_username, tv_usernameDrawer, tv_emailDrawer;
    private Button bt_ins,bt_web,bt_twi;
    private ImageView wel_image_profile, iv_image;

    Bitmap bitmap;

    Dialog myDialog;
    ViewFlipper v_flipper;

    //Tarjetas
    GridLayout mainGrid;

    int contador = 0;
    String nameGame = "Epidemic Zombie";


    //No mostrar más tarjeta
    private Switch s_saveLogin;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NOMORE = "nomore";

    private int nomore = 0;

    //Cambio de fotoPerfil
    Bitmap bmap;
    public static final String IMAGE = "image";
    private String image;

    public static final String PROFILE = "profile";
    private int profile = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myDialog = new Dialog(this);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Barra Principal verde
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Accion del boton flotante verde
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir una APP
                try{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.MoONStterInc.EZ");
                    startActivity(launchIntent);
                    Snackbar.make(view, "Abriendo Juego "+nameGame, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }catch (Exception e){
                    ShowNewGame2();
                    Snackbar.make(view, "Debes descargar "+nameGame, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        //Barra lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Marcar menu por defecto
        // navigationView.setCheckedItem(R.id.nav_home);

        //Para poder llamar a la instacia de nav_header_welcome
        View navHeader = navigationView.getHeaderView(0);
        tv_usernameDrawer = navHeader.findViewById(R.id.usernameDrawer);
        tv_emailDrawer = navHeader.findViewById(R.id.emailDrawer);
        //Slide Fotos
        int images [] = {R.drawable.welcome_slide1, R.drawable.welcome_slide2, R.drawable.welcome_slide3};
        v_flipper = findViewById(R.id.v_flipper);

        for(int image: images){
            flipperImages(image);
        }

        references();

        if(DataClass.GlobalUser == null){
            tv_username.setText("TestDummy");
            tv_usernameDrawer.setText("TestDummy");
            tv_emailDrawer.setText("testdummy@testdummy.com");
        }else{
            tv_username.setText(DataClass.GlobalUser.getUsername());
            tv_usernameDrawer.setText(DataClass.GlobalUser.getUsername());
            tv_emailDrawer.setText(DataClass.GlobalUser.getEmail());
        }

        //Mensaje principa de Buenos días.. etc...
        getTimeFromAndroid();

        //Obtener evento de click en las tarjetas
        setSingleEvent(mainGrid);

        //Button Ins
        bt_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WelcomeActivity.this, "No disponible", Toast.LENGTH_LONG).show();
                /*Uri uri = Uri.parse("http://www.moonstterinc.com/es/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });

        //Button Web
        bt_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.moonstterinc.com/es/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //Button Twi
        bt_twi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WelcomeActivity.this, "No disponible", Toast.LENGTH_LONG).show();
                /*Uri uri = Uri.parse("http://www.moonstterinc.com/es/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });


        loadData();
        updateViews();

        if (profile == 0){
            wel_image_profile.setBackgroundResource(R.drawable.drawer_profileme);
        }else{
            wel_image_profile.setBackgroundColor(Color.TRANSPARENT);
        }

        ShowNewGame();

    }

    //Declaraciones de references
    public void references(){
        tv_statusTime = findViewById(R.id.w_statusTime);
        tv_username = findViewById(R.id.w_username);
        mainGrid = findViewById(R.id.mainGrid);
        bt_ins = findViewById(R.id.welcome_ins);
        bt_web = findViewById(R.id.welcome_web);
        bt_twi = findViewById(R.id.welcome_twi);
        s_saveLogin = findViewById(R.id.s_saveNomore);
        wel_image_profile = findViewById(R.id.wel_image_profile);
        iv_image = findViewById(R.id.iv_image);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_web) {
            myDialog = new Dialog(this);
            ShowHelper();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            Intent Intent = new Intent(this, AboutActivity.class);
            startActivity(Intent);
        } else if (id == R.id.nav_settings) {
            Intent Intent = new Intent(this, SettingsActivity.class);
            startActivity(Intent);

        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
            myBuild.setMessage("¿Seguro que quieres cerrar sesión?");
            myBuild.setTitle("Epidemic Games");

            myBuild.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DataClass.profileFAIL = 1;
                    DataClass.color = 0;
                    DataClass.info = "¡Has cerrado sesión!";

                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            myBuild.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = myBuild.create();
            dialog.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Obtener mensaje
    private void getTimeFromAndroid() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 6 && timeOfDay < 12){
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenos días,");
        }else if(timeOfDay >= 12 && timeOfDay < 14){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas tardes,");
        }else if(timeOfDay >= 14 && timeOfDay < 21){
            //Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas tardes,");
        }else if(timeOfDay >= 21 && timeOfDay < 24) {
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas noches,");
        }else if(timeOfDay >24 && timeOfDay <=1) {
                //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
                tv_statusTime.setText("Buenas noches,");
        }else if(timeOfDay >1 && timeOfDay < 6) {
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas noches,");
        }
    }

    //Aniamacion de slide
    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        //Animación
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    //Mostar novedad de juego
    public void ShowNewGame() {

        if (nomore == 0){
            TextView txtclose;
            Button btnFollow;
            Switch s_saveNomore;
            myDialog.setContentView(R.layout.welcome_ez);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("");
            btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

            s_saveNomore = (Switch) myDialog.findViewById(R.id.s_saveNomore);
            s_saveNomore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "¡No lo volveremos a mostrar!", Toast.LENGTH_LONG).show();
                    nomore = 1;
                    saveData();

                }
                 }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }

    //Card si no tienes el juego
    public void ShowNewGame2() {
            TextView txtclose;
            Button btnFollow;
            Switch s_saveNomore;
            myDialog.setContentView(R.layout.welcome_ez2);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
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

    public void goPlayStore(View v){
        //final String appPackageName = "me.pou.app"; // getPackageName() from Context or Activity object
        final String appPackageName = "com.MoONStterInc.EpidemicZombie";
        try {
            myDialog.dismiss();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            myDialog.dismiss();
        }
    }

    //Mostrar mensaje de Ayuda
    public void ShowHelper() {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.welcome_help);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    //Evento al hacer click en la tajeta adecuada
    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (finalI){
                        case 0:
                            Intent intent = new Intent(WelcomeActivity.this,ProfileActivity.class);
                            //intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(WelcomeActivity.this,NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(WelcomeActivity.this,StatsActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(WelcomeActivity.this,GamesActivity.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(WelcomeActivity.this,GlobalActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            AlertDialog.Builder myBuild = new AlertDialog.Builder(WelcomeActivity.this);
                            myBuild.setMessage("¿Seguro que quieres salir?");
                            myBuild.setTitle("Epidemic Games");

                            myBuild.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Cierra todo
                                    finishAffinity();
                                }
                            });

                            myBuild.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog dialog = myBuild.create();
                            dialog.show();
                            break;
                    }
                }
            });
        }
    }

    //Al pusar atras con el boton de android sale un mensaje de estas seguro?
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setMessage("¿Seguro que quieres salir?");
        myBuild.setTitle("Epidemic Games");
        myBuild.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cierra todo
                finishAffinity();

            }
        });

        myBuild.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = myBuild.create();
        dialog.show();
    }

    //Prueba
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(NOMORE, nomore);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        nomore = sharedPreferences.getInt(NOMORE, nomore);


        image = sharedPreferences.getString(IMAGE,"");
        profile = sharedPreferences.getInt(PROFILE,  profile);
    }

    public void updateViews(){
        nomore =+ nomore;

        profile =+ profile;
        byte [] encodeByte= Base64.decode(image, Base64.DEFAULT);
        bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        wel_image_profile.setImageBitmap(bitmap);

    }
}
