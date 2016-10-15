package com.assgn.mad.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText edtTxtCity, edtTxtState;
    private RecyclerView rvSavedCities;
    private TextView txtViewSavedCities;
    private TextView txtViewNoSavedCities;
    private RelativeLayout layout;
    private LinearLayout linearLayout;
    private SavedCitiesAdapter adapter;
    List<City> cities;

    public static DatabaseDataManager dm;
    private SharedPreferences sharedPreferences;

    //URL setup
    public static final String API_KEY = "c145e85ff53d712456d2a094f94e404f";
    public static final String HOURLY_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?APPID=" + API_KEY + "&q=%s,%s";

    //Data Passing
    public static final String CITY_WEATHER_URL = "CITY_WEATHER_URL";
    public static final String CITY = "CITY";
    public static final String STATE = "STATE";
    public static final String SAVED_CITY_ITEMS = "SAVED_CITY_ITEMS ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseDataManager(this);
        edtTxtCity = (EditText) findViewById(R.id.editTextCity);
        edtTxtState = (EditText) findViewById(R.id.editTextState);
        rvSavedCities = (RecyclerView) findViewById(R.id.rvSavedCities);
        txtViewSavedCities = (TextView) findViewById(R.id.textViewSavedCitiesLable);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        layout = (RelativeLayout) findViewById(R.id.rootLayout);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutSearch);
        params1.addRule(RelativeLayout.BELOW, linearLayout.getId());
        params1.setMargins(0, 150, 10, 10);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        txtViewNoSavedCities = new TextView(MainActivity.this);
        txtViewNoSavedCities.setText(getResources().getString(R.string.text_lable_no_saved_cities));
        txtViewNoSavedCities.setTextSize(16);
        txtViewNoSavedCities.setGravity(Gravity.CENTER_HORIZONTAL);
        txtViewNoSavedCities.setTypeface(null, Typeface.BOLD);
        txtViewNoSavedCities.setTextColor(getResources().getColor(R.color.black));
        layout.addView(txtViewNoSavedCities, params1);

        //dm.saveCity(new City("Charlotte", "NC", 70, false, "14/10/2016"));
        //dm.saveCity(new City("Raleigh", "NC", 65, false, "10/22/2016"));
        //dm.saveCity(new City("New York", "NY", 66, false, "10/22/2016"));
        //dm.saveCity(new City("Sacramento", "CA", 67, false, "10/22/2016"));
        //dm.saveCity(new City("San Jose", "CA", 72, false, "10/22/2016"));

        cities = dm.getALL();
        moveFavouritesOnTop();
        setTemperatureAsPerType();
        setSavedCityItems();
    }

    /**
     * On Click Handler for search button
     *
     * @param view
     */
    public void searchAction(View view) {
        String city = edtTxtCity.getText().toString().trim();
        String state = edtTxtState.getText().toString().trim().toUpperCase();
        String urlCity;

        if (city.length() > 0 && state.length() > 0) {
            if (city.contains(" ")) {
                String cityName[] = city.split(" ");
                urlCity = makeCamelCase(cityName[0]) + "_" + makeCamelCase(cityName[1]);
                city = makeCamelCase(cityName[0]) + " " + makeCamelCase(cityName[1]);
            } else {
                urlCity = city;
            }
            String cityWeatherURL = String.format(HOURLY_FORECAST_URL, state, urlCity);

            if (isConnectedOnline()) {
                Intent nextActivity = new Intent(MainActivity.this, CityWeatherActivity.class);
                nextActivity.putExtra(CITY_WEATHER_URL, cityWeatherURL);
                nextActivity.putExtra(CITY, city);
                nextActivity.putExtra(STATE, state);
                startActivity(nextActivity);
            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.err_msg_no_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.err_msg_select_city_state), Toast.LENGTH_SHORT).show();
        }
    }

    private String makeCamelCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cities = dm.getALL();
        moveFavouritesOnTop();
        setTemperatureAsPerType();
        setSavedCityItems();
    }

    private void setSavedCityItems() {
        if (cities.size() > 0) {
            txtViewNoSavedCities.setVisibility(View.INVISIBLE);
            txtViewSavedCities.setVisibility(View.VISIBLE);

            adapter = new SavedCitiesAdapter(this, cities);
            rvSavedCities.setAdapter(adapter);
            rvSavedCities.setLayoutManager(new LinearLayoutManager(this));
        } else {
            txtViewNoSavedCities.setVisibility(View.VISIBLE);
            txtViewSavedCities.setVisibility(View.INVISIBLE);
        }
    }

    private void setTemperatureAsPerType() {
        String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
        if (pref_temp_type.equals("f")) {
            for (int i = 0; i < cities.size(); i++) {
                Double newTemp = (cities.get(i).getTemperature() * 1.8) + 32;
                cities.get(i).setTemperature(newTemp.intValue());
            }
        }

        /*if (pref_temp_type.equals("c") && isTemperatureSettingUpdated) {
            isTemperatureSettingUpdated = false;
            for (int i = 0; i < cities.size(); i++) {
                Double newTemp = (cities.get(i).getTemperature() - 32) / 1.8;
                cities.get(i).setTemperature(newTemp.intValue());
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_main_activity_menu) {
            Intent preferenceActivity = new Intent(MainActivity.this, PreferenceActivity.class);
            startActivity(preferenceActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    private void moveFavouritesOnTop() {
        /*Collections.sort(cities, new Comparator<City>(){
            public int compare(City c1, City c2){
                return c1.getId() < c2.getId() ? -1 : 1;
            }
        });*/

        int favouriteIndex = 0;
        City city;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).isFavourite()) {
                city = cities.get(i);
                cities.remove(i);
                cities.add(favouriteIndex, city);
                favouriteIndex++;
            }
        }
    }
}
