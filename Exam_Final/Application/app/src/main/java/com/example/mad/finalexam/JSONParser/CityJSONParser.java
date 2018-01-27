package com.example.mad.finalexam.JSONParser;

import com.example.mad.finalexam.POJO.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityJSONParser {
    public static ArrayList<City> parsePojo(String in) throws JSONException {
        ArrayList<City> pojoList = new ArrayList<City>();

        JSONObject root = new JSONObject(in);

        JSONArray pojoArray = root.getJSONArray("predictions");

        for (int i = 0; i < pojoArray.length(); i++) {
            JSONObject pojoArrayJSONObject = pojoArray.getJSONObject(i);

            City pojo = City.getPojo(pojoArrayJSONObject);

            pojoList.add(pojo);
        }
        return pojoList;
    }
}