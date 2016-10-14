package com.assgn.mad.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.assgn.mad.weatherapp.db.City;
import com.assgn.mad.weatherapp.db.DatabaseDataManager;

import java.util.List;

/**
 * Homework 06
 * MainActivity.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class MainActivity extends Activity {
    private DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseDataManager(this);

        dm.saveCity(new City("City 1", "Note 1 Text", 10, true, "date"));
        dm.saveCity(new City("Note 2", "Note 2 Text", 15, false, "date"));

        City city = dm.getCity(1);
        List<City> cities = dm.getALL();

        Log.d("demo", cities.toString());
    }
}
