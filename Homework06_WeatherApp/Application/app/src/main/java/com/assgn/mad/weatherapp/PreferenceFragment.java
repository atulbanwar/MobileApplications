package com.assgn.mad.weatherapp;

import android.os.Bundle;

/**
 * Homework 06
 * PreferenceFragment.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class PreferenceFragment extends android.preference.PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
