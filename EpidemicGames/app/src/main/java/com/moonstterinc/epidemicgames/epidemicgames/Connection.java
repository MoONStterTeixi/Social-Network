package com.moonstterinc.epidemicgames.epidemicgames;

import android.os.AsyncTask;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
<<<<<<< HEAD
import java.net.URI;
=======
>>>>>>> fde5a4637e9c1abfdc00ce50c3096149b72d1b8c
import java.net.URL;

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
