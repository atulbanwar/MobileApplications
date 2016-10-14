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
        double temperature=0;
        String medianImageUrl = null;
        ArrayList<HourlyWeather> dayWiseHourlyList = new ArrayList<HourlyWeather>();
        for (HourlyWeather hWeather : weatherList) {
            String currentDate = hWeather.getDate();
            if (currentDate.equals(oldDate) || oldDate == null) {
                dayWiseHourlyList.add(hWeather);

                oldDate = currentDate;


                if( hWeather.getTime().equals("09:00 AM"))
                {
                    medianImageUrl=hWeather.getIconUrl();
                }
                temperature =temperature+hWeather.getTemperature();

                if (counter + 1 == weatherList.size()) {
                    DailyWeather dw = processDailyWeather(dayWiseHourlyList, temperature, oldDate,medianImageUrl);
                    dailyWeathers.add(dw);
                }
            } else {


                DailyWeather dw= processDailyWeather(dayWiseHourlyList, temperature, oldDate,medianImageUrl);
                dailyWeathers.add(dw);

                //Resetting temperature
                temperature=0.0;

                //Resetting ImageIconUrl
                medianImageUrl=null;

                dayWiseHourlyList = null;
                dayWiseHourlyList = new ArrayList<HourlyWeather>();

                oldDate = currentDate;

                dayWiseHourlyList.add(hWeather);
            }
            counter++;
        }
        return dailyWeathers;
    }

    private static DailyWeather processDailyWeather(ArrayList<HourlyWeather> dayWiseHourlyList, double temperature, String date , String medianImageUrl) {
        DailyWeather dw = new DailyWeather(dayWiseHourlyList);

        //Setting median Temperature for each day
        double medianTemp = temperature / dayWiseHourlyList.size();
        dw.setMedianTemprature(medianTemp);

        //Setting median imageUrl
        if(medianImageUrl !=null)
        {
            dw.setMedianImageUrl(medianImageUrl);
        }else {
            dw.setMedianImageUrl(dayWiseHourlyList.get(0).getIconUrl());
        }

        //Setting Date for the Day
        dw.setDate(date);
        return dw;
    }
}