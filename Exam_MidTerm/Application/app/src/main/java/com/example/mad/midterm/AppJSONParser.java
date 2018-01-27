package com.example.mad.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by atulb on 10/24/2016.
 */

public class AppJSONParser {
    static ArrayList<App> pasrseApp(String in) throws JSONException {
        ArrayList<App> appsList = new ArrayList<App>();

        JSONObject root = new JSONObject(in);
        JSONObject feedObj = root.getJSONObject("feed");
        JSONArray appArray = feedObj.getJSONArray("entry");

        for (int i = 0; i < appArray.length(); i++) {
            JSONObject appArrayJSONObject = appArray.getJSONObject(i);

            App question = App.getApp(appArrayJSONObject);

            appsList.add(question);
        }

        return appsList;
    }
}
