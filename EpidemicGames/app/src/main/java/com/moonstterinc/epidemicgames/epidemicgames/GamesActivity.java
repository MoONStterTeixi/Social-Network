package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class GamesActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    private static final String URL_DATA = "https://ws.moonstterinc.com/query.php?action=getgames";

    Dialog myDialog;
    String appPackageName;

    private TextView tv_alert, tv_alert2;
    private ImageView im_alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        myDialog = new Dialog(this);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reference();

        myDialog = new Dialog(this);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();

        //Tarjetas en Horizontal
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(GamesActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
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

    public void reference(){
        tv_alert = findViewById(R.id.games_alert);
        tv_alert2 = findViewById(R.id.games_alert2);
        im_alert = findViewById(R.id.games_imalert);
    }

    private void loadRecyclerViewData(){

        DataClass.myAdapter = 1;
        //Tarjeta cargando + carga datos de la base de datos
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando noticias...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();

                        try {
                            //Añadimos el objecto JSON
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("games");


                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("name"),
                                        o.getString("about"),
                                        o.getString("desc"),
                                        o.getString("tag"),
                                        o.getString("date"),
                                        o.getString("text"),
                                        o.getString("image")
                                );



                                listItems.add(item);
                            }

                            if (array.length() == 0){
                                Toast.makeText(GamesActivity.this, "Compruebe la conexión o vuelva a intentarlo más tarde :)", Toast.LENGTH_LONG).show();
                            }
                            adapter = new MyAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            tv_alert2.setTextColor(Color.RED);
                            tv_alert.setTextColor(Color.WHITE);
                            im_alert.setBackgroundResource(R.drawable.no_foun);
                            Toast.makeText(GamesActivity.this, "Compruebe la conexión o vuelva a intentarlo más tarde :)", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void ShowInfoGame(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.games_infogame);
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

    public void goGame(View v){
        appPackageName = "me.pou.app"; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

}
