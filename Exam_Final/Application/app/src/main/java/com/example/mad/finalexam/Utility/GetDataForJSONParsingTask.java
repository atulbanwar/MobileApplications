package com.example.mad.finalexam.Utility;

import android.os.AsyncTask;

import com.example.mad.finalexam.POJO.PojoForJSONParsing;
import com.example.mad.finalexam.JSONParser.PojoJSONParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetDataForJSONParsingTask extends AsyncTask<String, Void, ArrayList<PojoForJSONParsing>> {
    IJSONParsedData activity;

    public GetDataForJSONParsingTask(IJSONParsedData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<PojoForJSONParsing> doInBackground(String... params) {
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
                return PojoJSONParser.parsePojo(sb.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PojoForJSONParsing> s) {
        super.onPostExecute(s);

        // ____________________ SORT BY DATE ____________________
        /*Collections.sort(s, new Comparator<PojoForJSONParsing>() {
            @Override
            public int compare(PojoForJSONParsing o1, PojoForJSONParsing o2) {
                return o1.getVariable4().compareTo(o2.getVariable4());
            }
        });*/

        activity.setupJSONParsedData(s);
    }

    static public interface IJSONParsedData {
        public void setupJSONParsedData(ArrayList<PojoForJSONParsing> result);
    }
}