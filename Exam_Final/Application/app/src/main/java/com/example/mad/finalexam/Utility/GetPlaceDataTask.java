package com.example.mad.finalexam.Utility;

import android.os.AsyncTask;

import com.example.mad.finalexam.JSONParser.PlaceJSONParser;
import com.example.mad.finalexam.POJO.Place;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetPlaceDataTask extends AsyncTask<String, Void, ArrayList<Place>> {
    IJSONParsedData activity;

    public GetPlaceDataTask(IJSONParsedData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Place> doInBackground(String... params) {
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
                return PlaceJSONParser.parsePojo(sb.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Place> s) {
        super.onPostExecute(s);

        // ____________________ SORT BY DATE ____________________
        /*Collections.sort(s, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getVariable4().compareTo(o2.getVariable4());
            }
        });*/

        activity.setupPlaceJSONParsedData(s);
    }

    static public interface IJSONParsedData {
        public void setupPlaceJSONParsedData(ArrayList<Place> result);
    }
}