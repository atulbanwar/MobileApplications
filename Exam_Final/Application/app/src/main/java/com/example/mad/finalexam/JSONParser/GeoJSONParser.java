package com.example.mad.finalexam.JSONParser;

import com.example.mad.finalexam.POJO.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeoJSONParser {
    public static String parsePojo(String in) throws JSONException {
        String geo = "";

        JSONObject root = new JSONObject(in);

        if (root.has("result")) {
            JSONObject result = root.getJSONObject("result");

            if (result.has("geometry")) {
                JSONObject geometry = result.getJSONObject("geometry");

                if (geometry.has("location")) {
                    JSONObject location = geometry.getJSONObject("location");

                    if (location.has("lat") && location.has("lng")) {
                        geo = location.getString("lat");
                        geo += "," + location.getString("lng");
                    }
                }
            }
        }

        return geo;
    }
}