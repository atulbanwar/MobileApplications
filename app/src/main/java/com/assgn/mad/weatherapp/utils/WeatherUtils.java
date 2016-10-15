package com.assgn.mad.weatherapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static String getWindDirection(String directionString) {
        Double direction = 0.0;
        try {
            direction = Double.parseDouble(directionString);
        } catch (NumberFormatException ex) {
            return ex.getLocalizedMessage();
        }
        int value = (int) ((direction / 22.5) + 0.5);
        return WIND_DIRECTION[value % 16];
    }


    public static double getWindDirectionDegree(double directionDegree)
    {
        return (Math.round(directionDegree * 100.0) / 100.0);
    }

    public static String getFormattedDate(String dateString) {
        Date requireDate = null;
        try {
            requireDate = parseDate(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formattedDate = new SimpleDateFormat("MMM dd, yyyy");
        return formattedDate.format(requireDate);
    }

    public static String getFormattedTime(String timeString) {
        Date requireTime= null;
        try {
            requireTime = parseDate(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formattedTime = new SimpleDateFormat("hh:mm a");
        return formattedTime.format(requireTime);
    }

    public static Date parseDate(String dateString) throws ParseException {


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return df.parse(dateString);

    }

    public static double fahrenheitToCelsius( double fahrenheit) {

        return ((fahrenheit-32)/1.8);

    }
}
