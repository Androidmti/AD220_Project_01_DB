/*
 * PrefsFragment.java
 * Daniel Borodenko
 */
package com.mti.ad220_project_01_db;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

public class PrefsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	private final int CIRCLE_LIMIT = 100;
	private final int SIZE_LIMIT = 200;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		// register preference change listener
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	} // onCreate(Bundle savedInstanceState)

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreference, String key) {
		
		// check to see if number of circles is not more than CIRCLE_LIMIT
		int number = Integer.valueOf(sharedPreference.getString("pref_number", "10"));
		if (number > CIRCLE_LIMIT) {

			// if more than CIRCLE_LIMIT, set preference to CIRCLE_LIMIT
			EditTextPreference etPreference = (EditTextPreference) findPreference(key);
			etPreference.setText(String.valueOf(CIRCLE_LIMIT));
		}

		// check to see if size of circles is not bigger than SIZE_LIMIT
		int size = Integer.valueOf(sharedPreference.getString("pref_size", "100"));
		if (size > SIZE_LIMIT) {

			// if more than SIZE_LIMIT, set preference to SIZE_LIMIT
			EditTextPreference etPreference = (EditTextPreference) findPreference(key);
			etPreference.setText(String.valueOf(SIZE_LIMIT));
		}
			
		// set summaries for preferences to display current values
		setSummaries(findPreference(key));
	} // onSharedPreferenceChanged(SharedPreferences sharedPreference, String key)

	private void setSummaries(Preference preference) {
		
		// set summaries of preferences to display current values
		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			listPreference.setSummary(listPreference.getEntry());
		}

		else if (preference instanceof EditTextPreference) {
			EditTextPreference editTextPref = (EditTextPreference) preference;
			preference.setSummary(editTextPref.getText());
		}
	} // setSummaries(Preference preference)

	@Override
	public void onResume() {
		super.onResume();

		// re-register listener on resume 
		getPreferenceScreen().getSharedPreferences()
			.registerOnSharedPreferenceChangeListener(this);
		
		// loop through preferences and set summaries to display current values
		// this is used to display summaries of preferences when initially loading
		// preference activity
		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
			Preference preference = getPreferenceScreen().getPreference(i);
			if (preference instanceof PreferenceGroup) {
				
				// currently there are no PreferenceGroups, this is for future releases
				PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
				for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
					setSummaries(preferenceGroup.getPreference(j));
				}
			} else {
				setSummaries(preference);
			}
		}
	} // onResume()

	@Override
	public void onPause() {
		super.onPause();

		// un-register listener on pause
		getPreferenceScreen().getSharedPreferences()
			.unregisterOnSharedPreferenceChangeListener(this);
	} // onPause()
	

} // class PrefsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
