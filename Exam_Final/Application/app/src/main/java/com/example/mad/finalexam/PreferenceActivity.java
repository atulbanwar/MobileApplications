package com.example.mad.finalexam;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;

public class PreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preference);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFragment())
                .commit();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("preference_type")) {
                    String pref_temp_type = sharedPreferences.getString("preference_type", "");
                    if (pref_temp_type.equals("1")) {
                        // Toast
                    } else {
                        //Toast.makeText(PreferenceActivity.this, getResources().getString(R.string.msg_temperature_change_fahrenheit), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
