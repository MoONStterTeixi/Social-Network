package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Calendar;

public class Welcomev2Activity extends AppCompatActivity {

    private TextView tv_username, tv_statusTime;
    GridLayout mainGrid;
    ViewFlipper v_flipper;
    Dialog myDialog;

    String nameGame = "Epidemic Zombie";
    int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_welcomev2);

        references();
        getTimeFromAndroid();


        //ShowNewGame();



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

        //Set Event
        setSingleEvent(mainGrid);

        int images [] = {R.drawable.welcome_slide1, R.drawable.welcome_slide2, R.drawable.welcome_slide3};

        for(int image: images){
            flipperImages(image);
        }

        if(DataClass.GlobalUser == null){
            tv_username.setText("TestDummy");
        }else{
            tv_username.setText(DataClass.GlobalUser.getUsername());
        }

        if (contador == 0){
            //ShowNewGame();
            contador ++;
        }
    }

    public void references(){
        tv_username = findViewById(R.id.w_username);
        tv_statusTime = findViewById(R.id.textGrid2);
        mainGrid = findViewById(R.id.mainGrid);
        v_flipper = findViewById(R.id.v_flipper);
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

    //Crear un toggle event
    /*private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(MainActivity.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(MainActivity.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

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
                            Intent intent = new Intent(Welcomev2Activity.this,ProfileActivity.class);
                            //intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(Welcomev2Activity.this,NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:

                            break;
                        case 6:

                            break;
                    }

                    //Un solo layout
                    /*Intent intent = new Intent(MainActivity.this,ActivityOne.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);*/

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
}
