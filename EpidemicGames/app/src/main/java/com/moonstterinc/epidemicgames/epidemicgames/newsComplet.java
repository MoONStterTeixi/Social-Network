package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class newsComplet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Añadimos todos los label que tenga
        setContentView(R.layout.activity_news_complet);

        //Declarar Variables
        TextView txtInfo = (TextView)findViewById(R.id.head);
        TextView txtDesc = (TextView)findViewById(R.id.desc);
        TextView txtTag = (TextView)findViewById(R.id.tag);
        TextView txtDate = (TextView)findViewById(R.id.date);
        TextView txtText = (TextView)findViewById(R.id.text);
        ImageView imImage = (ImageView) findViewById(R.id.imageView);



        //Insertarlas en cada sitio
        if(getIntent() != null)
        {
            String info = getIntent().getStringExtra("head");
            txtInfo.setText(info);
            String desc = getIntent().getStringExtra("desc");
            txtDesc.setText(desc);
            String tag = getIntent().getStringExtra("tag");
            txtTag.setText(tag);
            String date = getIntent().getStringExtra("date");
            txtDate.setText(date);
            String text = getIntent().getStringExtra("text");
            txtText.setText(text);
            String img =  getIntent().getStringExtra("img");
            Picasso.get().load(img).into(imImage);
        }
    }

    public void reference(){

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
}
