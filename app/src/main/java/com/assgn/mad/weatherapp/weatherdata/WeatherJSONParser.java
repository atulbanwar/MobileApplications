package com.assgn.mad.weatherapp.weatherdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Homework 05
 * WeatherJSONParser.java
 * Sanket Patil
 * Atul Banwar
 */

public class WeatherJSONParser {
    static ArrayList<DailyWeather> pasrseWeather(String in) throws JSONException {
        ArrayList<HourlyWeather> weatherList = new ArrayList<HourlyWeather>();
        ArrayList<DailyWeather> dailyWeathers = new ArrayList<DailyWeather>();
        JSONObject root = new JSONObject(in);

        if (root.has("list")) {
            JSONArray weatherArray = root.getJSONArray("list");

            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherArrayJSONObject = weatherArray.getJSONObject(i);
                HourlyWeather weather = HourlyWeather.getWeather(weatherArrayJSONObject);
                weatherList.add(weather);
            }

        }

        String oldDate = null;
        int counter = 0;
        ArrayList<HourlyWeather> dayWiseHourlyList = new ArrayList<HourlyWeather>();
        for (HourlyWeather hWeather : weatherList) {
            String currentDate = hWeather.getDate();
            if (currentDate.equals(oldDate) || oldDate == null) {
                dayWiseHourlyList.add(hWeather);

                oldDate = currentDate;

                if (counter + 1 == weatherList.size()) {
                    DailyWeather dw = new DailyWeather(dayWiseHourlyList);
                    dailyWeathers.add(dw);
                }
            } else {
                DailyWeather dw = new DailyWeather(dayWiseHourlyList);
                dailyWeathers.add(dw);
                dayWiseHourlyList = null;
                dayWiseHourlyList = new ArrayList<HourlyWeather>();

                oldDate = currentDate;

                dayWiseHourlyList.add(hWeather);
            }
            counter++;
        }
        return dailyWeathers;
    }
}