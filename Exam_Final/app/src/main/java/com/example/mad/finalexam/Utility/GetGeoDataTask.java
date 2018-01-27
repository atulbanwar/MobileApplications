package com.example.mad.finalexam.Utility;

import android.os.AsyncTask;

import com.example.mad.finalexam.JSONParser.CityJSONParser;
import com.example.mad.finalexam.JSONParser.GeoJSONParser;
import com.example.mad.finalexam.POJO.City;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetGeoDataTask extends AsyncTask<String, Void, String> {
    IJSONParsedData activity;

    public GetGeoDataTask(IJSONParsedData activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                return GeoJSONParser.parsePojo(sb.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // ____________________ SORT BY DATE ____________________
        /*Collections.sort(s, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getVariable4().compareTo(o2.getVariable4());
            }
        });*/

        activity.setupGeoJSONParsedData(s);
    }

    static public interface IJSONParsedData {
        public void setupGeoJSONParsedData(String result);
    }
}