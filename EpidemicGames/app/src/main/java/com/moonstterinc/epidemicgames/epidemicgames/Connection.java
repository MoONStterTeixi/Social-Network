package com.moonstterinc.epidemicgames.epidemicgames;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... parm) {
        HttpURLConnection conn = null;
        try {

            URL url = new URL(parm[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = "aaa";
            inputLine = in.readLine();
            in.close();
            DataClass.UserJson = inputLine;

            return "Done";

        }catch (Exception e){
            return "fail";
        }
    }
}
