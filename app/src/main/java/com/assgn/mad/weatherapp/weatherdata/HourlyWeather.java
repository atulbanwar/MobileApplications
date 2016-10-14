package com.assgn.mad.weatherapp.weatherdata;

import com.assgn.mad.weatherapp.utils.WeatherUtils;

import org.json.JSONArray;
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
    private String date;
    private String iconUrl;
    private double temperature;
    private String condition;
    private double pressure;
    private String humidity;
    private String windSpeed;
    private String windDirection;
    private double windDirectionDegree;

    private static final String ICON_URL = "http://api.openweathermap.org/img/w/%s.png";

    public HourlyWeather() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindDirectionDegree() {
        return windDirectionDegree;
    }

    public void setWindDirectionDegree(double windDirectionDegree) {
        this.windDirectionDegree = windDirectionDegree;
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


    public static HourlyWeather getWeather(JSONObject obj) throws JSONException {
        String iconCode = null;
        String direction = null;
        HourlyWeather hourlyWeather = new HourlyWeather();

        //To fetch icon code and Description
        JSONArray weatherArray = obj.getJSONArray("weather");
        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject weatherArrayJSONObject = weatherArray.getJSONObject(i);
            if(!weatherArrayJSONObject.getString("icon").isEmpty())
            {
                iconCode=weatherArrayJSONObject.getString("icon");
            }

            if(!weatherArrayJSONObject.getString("description").isEmpty())
            {
                hourlyWeather.setCondition(weatherArrayJSONObject.getString("description"));
            }
        }
        hourlyWeather.setIconUrl(String.format(ICON_URL, iconCode));



        //To fetch temp, pressure and humidity
        JSONObject tempObj = obj.getJSONObject("main");
        hourlyWeather.setTemperature((((tempObj.getDouble("temp"))*9/5)-459.67));
        hourlyWeather.setPressure(tempObj.getDouble("pressure"));
        hourlyWeather.setPressure(tempObj.getDouble("humidity"));

        //To fetch wind speed and direction
        JSONObject windObj = obj.getJSONObject("wind");
        hourlyWeather.setWindSpeed(windObj.getString("speed"));
        double windDirectionDegree= windObj.getDouble("deg");
        hourlyWeather.setWindDirectionDegree(WeatherUtils.getWindDirectionDegree(windDirectionDegree));
        try {
            direction = WeatherUtils.getWindDirection(String.valueOf(windDirectionDegree));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (direction != null) {
            hourlyWeather.setWindDirection(direction);
        }

        //To fetch Time and Date;
        String dateTimeJSON = obj.getString("dt_txt");
        hourlyWeather.setDate(WeatherUtils.getFormattedDate(dateTimeJSON));

        hourlyWeather.setTime(WeatherUtils.getFormattedTime(dateTimeJSON));
        return hourlyWeather;
    }

}
