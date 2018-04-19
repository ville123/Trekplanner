package com.trekplanner.app.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trekplanner.app.R;

/**
 *
 */
public class PreferenceActivity extends android.preference.PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    public PreferenceActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        initSummaries(getPreferenceScreen());

    }

    private void initSummaries(Preference pref) {
        if (pref instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) pref;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummaries(pGrp.getPreference(i));
            }
        } else {
            updateSummaries(pref);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        updateSummaries(findPreference(key));
    }

    private void updateSummaries(Preference pref) {
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            if (etp!=null)
                pref.setSummary(pref.getSummary().toString().replaceAll(":.*", ": " + etp.getText()));
        } else if (pref instanceof ListPreference) {
            ListPreference lstPref = (ListPreference) pref;
            if (lstPref != null && lstPref.getEntry() != null)
                pref.setSummary(pref.getSummary().toString().replaceAll(":.*", ": " + lstPref.getEntry().toString()));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
