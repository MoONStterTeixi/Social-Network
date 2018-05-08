package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Intent;
import android.net.Network;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        references();

        //Para el Navigation Drawer
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
    }

    public void references(){
        mDrawerlayout = findViewById(R.id.drawer);
        nvDrawer =  findViewById(R.id.nav);
    }

    //Para el Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    //Navigation Drawer Opciones segun el caso
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()){
            case R.id.options:
                fragmentClass = OptionsFragment.class;
                break;
            case R.id.logout:
                //Falta poner el salir
                break;
             default:
                 //fragmentClass = OptionsFragment.class;
                 Toast.makeText(this, "ERROR NOT FOUND 404", Toast.LENGTH_LONG).show();
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();

        }catch(Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();
    }

    //Navigation Drawer
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });
    }

    //Volver a Inicio
    public void goBack (View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
        finish();
    }
}
