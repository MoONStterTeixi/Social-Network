package com.moonstterinc.epidemicgames.epidemicgames;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassCallPHPfile extends AsyncTask<String, String, String> {
    StringBuilder result;

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("email", DataClass.email);
                jObj.put("password",DataClass.pwd);
            } catch (JSONException e) {
            }
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jObj.toString());
            wr.flush();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                DataClass.msg = result.toString();

            } else {
                InputStream err = conn.getErrorStream();

            }

        } catch (MalformedURLException e) {

        } catch (IOException e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

}
