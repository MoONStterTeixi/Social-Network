package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Calendar;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_statusTime, tv_username, tv_emailDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        references();

        if(DataClass.GlobalUser == null){
            tv_username.setText("TestDummy");
        }else{
            tv_username.setText(DataClass.GlobalUser.getUsername());
        }
        getTimeFromAndroid();
        onInsta();
        //createNotification();
    }

    public void onInsta() {
        ImageView entry = (ImageView) findViewById(R.id.welcome_insta);
        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
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
            Intent Intent = new Intent(this, WebActivity.class);
            startActivity(Intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
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

    //Codigo a mano
    public void references(){
        tv_statusTime = findViewById(R.id.statusTime);
        tv_username = findViewById(R.id.username);
        tv_emailDrawer = findViewById(R.id.emailDrawer);
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
