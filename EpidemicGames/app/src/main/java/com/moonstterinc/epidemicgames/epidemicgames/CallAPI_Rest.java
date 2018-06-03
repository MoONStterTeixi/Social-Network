package com.moonstterinc.epidemicgames.epidemicgames;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CallAPI_Rest extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... parm) {
        DataClass.UserJson ="";
        HttpURLConnection conn = null;
        try
        {
            URL oracle = new URL(parm[0]);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                DataClass.UserJson += inputLine;
            in.close();
        }
        catch (MalformedURLException e) { }
        catch (IOException e) { }
        finally
        {
            if (conn != null)
            {
                conn.disconnect();
            }
        }
        return null;
    }
}
