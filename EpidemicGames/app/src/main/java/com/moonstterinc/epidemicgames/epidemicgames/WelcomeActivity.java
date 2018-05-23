package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import java.util.Calendar;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_statusTime, tv_username, tv_usernameDrawer, tv_emailDrawer;
    Dialog myDialog;
    ViewFlipper v_flipper;

    int contador = 0;
    String nameGame = "Epidemic Zombie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myDialog = new Dialog(this);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir una APP
                try{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);
                    Snackbar.make(view, "Abriendo Juego "+nameGame, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }catch (Exception e){
                    ShowNewGame();
                    Snackbar.make(view, "Debes descargar "+nameGame, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Marcar menu por defecto
        navigationView.setCheckedItem(R.id.nav_home);

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
        getTimeFromAndroid();
        //createNotification();
        if (contador == 0){
            ShowNewGame();
            contador ++;
        }
    }

    //Codigo a mano
    public void references(){
        tv_statusTime = findViewById(R.id.statusTime);
        tv_username = findViewById(R.id.username);
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

    /*public void onInsta() {
        ImageView entry = (ImageView) findViewById(R.id.welcome_insta);
        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }*/

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

    public void ShowNewGame() {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.welcome_ez);
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

    public void goPlayStore(View v){
        final String appPackageName = "me.pou.app"; // getPackageName() from Context or Activity object
        try {
            myDialog.dismiss();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            myDialog.dismiss();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (id != id) {
                Intent Intent = new Intent(this, WelcomeActivity.class);
                startActivity(Intent);
            }
        }else if (id == R.id.nav_profile) {
            Intent Intent = new Intent(this, ProfileActivity.class);
            startActivity(Intent);
        } else if (id == R.id.nav_news) {
            Intent Intent = new Intent(this, NewsActivity.class);
            startActivity(Intent);
        } else if (id == R.id.nav_games) {
            Intent Intent = new Intent(this, GamesActivity.class);
            startActivity(Intent);
        } else if (id == R.id.nav_about) {
            Intent Intent = new Intent(this, AboutActivity.class);
            startActivity(Intent);
        } else if (id == R.id.nav_settings) {
            Intent Intent = new Intent(this, SettingsActivity.class);
            startActivity(Intent);

        } else if (id == R.id.nav_exit) {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Obtener mensaje
    private void getTimeFromAndroid() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenos días,");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            //Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas Tardes,");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            //Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas Tardes,");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            //Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            tv_statusTime.setText("Buenas Noches,");
        }
    }

    //Creamos notificaciones
    /*public void createNotification() {
        // Prepare intent which is triggered if the
        Intent intent = new Intent(this, WelcomeActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notifications = new Notification.Builder(this)
                .setContentTitle("New notification EpidemicGames")
                .setContentText("Thanks for using this app :)").setSmallIcon(R.drawable.icon)
                .setContentIntent(pending)
                .addAction(R.drawable.ic_menu_send, "Reply", pending) //Añadir Banner Largo x3
                .addAction(R.drawable.ic_menu_share, "cancel", pending)
                .addAction(R.drawable.ic_menu_slideshow, "setings", pending).build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifications.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notifications);
    }*/

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

}
