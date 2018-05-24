package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class Welcomev2Activity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomev2);

       // mainGrid = findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
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
                            Intent intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(Welcomev2Activity.this,ActivityOne.class);
                            intent.putExtra("info","This is activity from card item index  "+finalI);
                            startActivity(intent);
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
}