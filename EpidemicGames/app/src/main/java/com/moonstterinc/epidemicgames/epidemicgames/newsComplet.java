package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class newsComplet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_news_complet);

        TextView txtInfo = (TextView)findViewById(R.id.head);
        TextView txtDesc = (TextView)findViewById(R.id.desc);

        if(getIntent() != null)
        {
            String info = getIntent().getStringExtra("head");
            txtInfo.setText(info);
            String desc = getIntent().getStringExtra("desc");
            txtDesc.setText(desc);
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
