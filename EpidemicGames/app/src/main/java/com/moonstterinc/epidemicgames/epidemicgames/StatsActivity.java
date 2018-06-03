package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatsActivity extends AppCompatActivity {

    private  TextView textViewXp,textViewLife,textViewDama,textViewMoney,textViewRound,textViewNick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        References();

        try {
            //Stats
            LoadData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void References(){
        textViewXp = findViewById(R.id.textViewXP);
        textViewLife = findViewById(R.id.textViewLife);
        textViewDama = findViewById(R.id.textViewDama);
        textViewMoney = findViewById(R.id.textViewMoney);
        textViewRound = findViewById(R.id.textViewRound);
        textViewNick = findViewById(R.id.textViewNick);
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
    public void LoadData() throws ExecutionException, InterruptedException, JSONException {
        new CallAPI_Rest().execute("https://moonstterinc.000webhostapp.com/EZ/query.php?action=getusr&json={\"username\":\""+DataClass.GlobalUser.getUsername()+"\"}").get();

        Log.d("tag",DataClass.UserJson);

        textViewNick.setText(DataClass.GlobalUser.getUsername());
        JSONObject o = new JSONObject(DataClass.UserJson);
        textViewXp.setText(o.getString("experience"));
        textViewLife.setText(o.getString("VidaMax"));
        textViewDama.setText(o.getString("DmgRange"));
        textViewMoney.setText(o.getString("money"));
        textViewRound.setText(o.getString("act_round"));
    }
}
