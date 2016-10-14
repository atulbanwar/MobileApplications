package com.assgn.mad.weatherapp.com.assgn.mad.weatherapp.utils;

/**
 * Homework 06
 * WeatherUtils.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class WeatherUtils {

    private static String[] WIND_DIRECTION = {"N", "NNE", "NE", "ENE",
            "E", "ESE", "SE", "SSE",
            "S", "SSW", "SW", "WSW",
            "W", "WNW", "NW", "NNW"};

    /**
     * Returns Direction from degree
     *
     * @param directionString
     * @return
     */
    public static String GetWindDirection(String directionString) {
        Double direction = 0.0;
        try {
            direction = Double.parseDouble(directionString);
        } catch (NumberFormatException ex) {
            return ex.getLocalizedMessage();
        }
        int value = (int) ((direction / 22.5) + 0.5);
        return WIND_DIRECTION[value % 16];
    }
}
