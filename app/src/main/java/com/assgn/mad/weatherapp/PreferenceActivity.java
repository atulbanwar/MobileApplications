package com.assgn.mad.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class PreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenceFragment())
                .commit();

        SharedPreferences sharedPreferences = sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("preference_temperature_type")) {
                    CityWeatherActivity.isTemperatureSettingUpdated = true;

                    String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
                    if (pref_temp_type.equals("c")) {
                        Toast.makeText(PreferenceActivity.this, getResources().getString(R.string.msg_temperature_change_celsius), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PreferenceActivity.this, getResources().getString(R.string.msg_temperature_change_fahrenheit), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}