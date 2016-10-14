package com.assgn.mad.weatherapp.weatherdata;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Homework 05
 * HourlyWeather.java
 * Sanket Patil
 * Atul Banwar
 */

public class HourlyWeather implements Serializable {
    private String time;
    private String iconUrl;
    private String temperature;
    private String condition;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String windDirection;

    public HourlyWeather(String time, String iconUrl,
                         String temperature, String condition,
                         String pressure, String humidity,
                         String windSpeed, String windDirection ) {
        this.time = time;
        this.temperature = temperature;
        this.iconUrl = iconUrl;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.condition = condition;
        this.humidity = humidity;
        this.pressure = pressure;
    }


    /* 	{
            "dt": 1476414000,
			"main": {
				"temp": 281.71,
				"temp_min": 281.71,
				"temp_max": 282.283,
				"pressure": 1014.58,
				"sea_level": 1022.19,
				"grnd_level": 1014.58,
				"humidity": 82,
				"temp_kf": -0.57
			},
			"weather": [
				{
					"id": 500,
					"main": "Rain",
					"description": "light rain",
					"icon": "10n"
				}
			],
			"clouds": {
				"all": 32
			},
			"wind": {
				"speed": 2.41,
				"deg": 94.502
			},
			"rain": {
				"3h": 0.04
			},
			"sys": {
				"pod": "n"
			},
			"dt_txt": "2016-10-14 03:00:00"
		}*/


 /*    public static HourlyWeather getWeather(JSONObject obj) throws JSONException {
        //HourlyWeather hourlyWeather = new HourlyWeather();

       JSONObject timeObj = obj.getJSONObject("FCTTIME");
        hourlyWeather.setTime(timeObj.getString("pretty"));

        JSONObject tempObj = obj.getJSONObject("temp");
        hourlyWeather.setTemperature(tempObj.getString("english"));
        hourlyWeather.setMaximumTemperature(tempObj.getString("english"));
        hourlyWeather.setMinimumTemperature(tempObj.getString("english"));

        JSONObject dewPointObj = obj.getJSONObject("dewpoint");
        hourlyWeather.setDewPoint(dewPointObj.getString("english"));

        hourlyWeather.setClouds(obj.getString("condition"));
        hourlyWeather.setIconUrl(obj.getString("icon_url"));

        JSONObject windSpeedObj = obj.getJSONObject("wspd");
        hourlyWeather.setWindSpeed(windSpeedObj.getString("english") + " mph");

        JSONObject windDirObj = obj.getJSONObject("wdir");
        String direction = windDirObj.getString("dir");
        direction = getFullDirection(direction);
        hourlyWeather.setWindDirection(windDirObj.getString("degrees") + "\u00B0 " + direction);

        hourlyWeather.setClimateType(obj.getString("wx"));
        hourlyWeather.setHumidity(obj.getString("humidity"));

        JSONObject feelsLikeObj = obj.getJSONObject("feelslike");
        hourlyWeather.setFeelsLike(feelsLikeObj.getString("english"));

        JSONObject pressureObj = obj.getJSONObject("mslp");
        hourlyWeather.setPressure(pressureObj.getString("metric"));

       // return hourlyWeather;
    }*/

    private static String getFullDirection(String direction) {
        if (direction.equals("E")) {
            direction = "East";
        } else if (direction.equals("W")) {
            direction = "West";
        } else if (direction.equals("S")) {
            direction = "South";
        } else if (direction.equals("N")) {
            direction = "North";
        } else if (direction.equals("ES")) {
            direction = "East South";
        } else if (direction.equals("EN")) {
            direction = "East North";
        } else if (direction.equals("WS")) {
            direction = "West South";
        } else if (direction.equals("WN")) {
            direction = "West North";
        } else if (direction.equals("SE")) {
            direction = "South East";
        } else if (direction.equals("SW")) {
            direction = "South West";
        } else if (direction.equals("NE")) {
            direction = "North East";
        } else if (direction.equals("NW")) {
            direction = "North West";
        }

        return direction;
    }

}
