package com.assgn.mad.weatherapp.weatherdata;

import java.util.List;

/**
 * Created by Sanket on 10/13/2016.
 */

public class DailyWeather {

    private List<HourlyWeather> hourlyWeathers;
    private String date;
    private String medianImageUrl;
    private double medianTemprature;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedianImageUrl() {
        return medianImageUrl;
    }

    public void setMedianImageUrl(String medianImageUrl) {
        this.medianImageUrl = medianImageUrl;
    }

    public double getMedianTemprature() {
        return medianTemprature;
    }

    public void setMedianTemprature(double medianTemprature) {
        this.medianTemprature = medianTemprature;
    }

    public DailyWeather(List<HourlyWeather> hourlyWeathers) {
        this.hourlyWeathers = hourlyWeathers;
    }

    public List<HourlyWeather> getHourlyWeathers() {
        return hourlyWeathers;
    }

    public void setHourlyWeathers(List<HourlyWeather> hourlyWeathers) {
        this.hourlyWeathers = hourlyWeathers;
    }
}
