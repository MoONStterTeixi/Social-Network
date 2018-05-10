package com.moonstterinc.epidemicgames.epidemicgames;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class Connection extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... parm) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(parm[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = "A";
            inputLine = in.readLine();
            in.close();

            DataClass.UserJson = inputLine;
            return "Done";

        }catch (Exception e){
            return "fail";
        }
    }
}
