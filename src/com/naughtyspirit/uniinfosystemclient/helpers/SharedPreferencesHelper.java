package com.naughtyspirit.uniinfosystemclient.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor mEditor;

	// Saving a string preference
	public static void setPreference(Context ctx, String key, String value) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		mEditor = mSharedPreferences.edit();

		mEditor.putString(key, value);
		mEditor.commit();
	}

	// Deleting a preference
	public static void deletePreference(Context ctx, String key) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		mEditor = mSharedPreferences.edit();

		mEditor.remove(key);
		mEditor.commit();
	}

	// Getting a string value
	public static String getStringPreference(Context ctx, String key) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		return mSharedPreferences.getString(key, null);
	}
}
