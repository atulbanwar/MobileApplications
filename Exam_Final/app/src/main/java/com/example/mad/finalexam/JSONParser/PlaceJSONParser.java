package com.example.mad.finalexam.JSONParser;

import com.example.mad.finalexam.POJO.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceJSONParser {
    public static ArrayList<Place> parsePojo(String in) throws JSONException {
        ArrayList<Place> pojoList = new ArrayList<Place>();

        JSONObject root = new JSONObject(in);

        JSONArray pojoArray = root.getJSONArray("results");

        for (int i = 0; i < pojoArray.length(); i++) {
            JSONObject pojoArrayJSONObject = pojoArray.getJSONObject(i);

            Place pojo = Place.getPojo(pojoArrayJSONObject);

            pojoList.add(pojo);
        }
        return pojoList;
    }
}