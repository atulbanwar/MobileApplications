package com.example.mad.finalexam.JSONParser;

import com.example.mad.finalexam.POJO.PojoForJSONParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PojoJSONParser {
    public static ArrayList<PojoForJSONParsing> parsePojo(String in) throws JSONException {
        ArrayList<PojoForJSONParsing> pojoList = new ArrayList<PojoForJSONParsing>();

        JSONObject root = new JSONObject(in);

        JSONArray pojoArray = root.getJSONArray("questions");

        for (int i = 0; i < pojoArray.length(); i++) {
            JSONObject pojoArrayJSONObject = pojoArray.getJSONObject(i);

            PojoForJSONParsing pojo = PojoForJSONParsing.getPojo(pojoArrayJSONObject);

            pojoList.add(pojo);
        }
        return pojoList;
    }
}