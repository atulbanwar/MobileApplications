package com.example.mad.finalexam;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
