package com.moonstterinc.epidemicgames.epidemicgames;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Connection extends Thread {

    private String Action;
    private User user;

    public Connection(String Action, User user){
        this.Action = Action;
        this.user = user;
    }

    public void run(){
        switch (Action){
            case "login":
                login();
                break;
            case "register":
                register();
                break;
            case "update":
                update();
                break;
        }
    }

    private void login(){

    }

    private void register(){

        /*String urlString = "http://172.17.129.63/Epidemic-Zombie-WebService/API-Rest/sn/query.php?action=register&json=" + user.toJson();
        try{
            URL  a = new URL(urlString);
            InputStream is = a.openStream();
        }catch (Exception e){
            Log.e("**************Error", ""+e);
        }
*/

    }

    private void update(){

    }

}
