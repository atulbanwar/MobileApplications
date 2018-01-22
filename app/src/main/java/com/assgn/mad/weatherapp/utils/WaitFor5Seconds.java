package com.assgn.mad.weatherapp.utils;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Homework 06
 * GetHourlyForcastData.java
 * Sanket Patil
 * Atul Banwar
 */

/**
 * If a city/state does not exist, then as per requirement the application should wait for 5 seconds and go back to mainActivity
 * Implementing the wait logic in AsyncTask, because Thread.Sleep in CityWeather activity will block the activity for 5 seconds and
 * won't allow the user to go back manually, if he/she wants to.
 */

public class WaitFor5Seconds extends AsyncTask<String, Void, Void> {
    IData activity;

    public WaitFor5Seconds(IData activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        activity.finishActivity();
    }

    static public interface IData {
        public void finishActivity();
    }
}
