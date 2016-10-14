package com.assgn.mad.weatherapp.weatherdata;

import java.util.List;

/**
 * Created by Sanket on 10/13/2016.
 */

public class DailyWeather {

    private List<HourlyWeather> hourlyWeathers;

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
