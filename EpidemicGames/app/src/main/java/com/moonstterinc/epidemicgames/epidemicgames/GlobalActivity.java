package com.moonstterinc.epidemicgames.epidemicgames;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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

public class GlobalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    private static final String URL_DATA = "https://ws.moonstterinc.com/query.php?action=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        //Evitar que rote *
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Boton lateral atras <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();
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

    private void loadRecyclerViewData(){

        DataClass.myAdapter = 2;
        //Tarjeta cargando + carga datos de la base de datos
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando jugadores...");
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
                            JSONArray array = jsonObject.getJSONArray("global");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("nickname"),
                                        o.getString("experience"),
                                        o.getString("act_round")
                                );
                                listItems.add(item);
                                //DataClass.id = i++,
                            }

                            adapter = new MyAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
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
}
