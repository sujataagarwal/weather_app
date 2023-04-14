package com.jpmorgan.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class AppPreferences(context: Context) {

    private val _sharedPrefs: SharedPreferences
    private val _prefsEditor: SharedPreferences.Editor

    init {
        _sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
        _prefsEditor = _sharedPrefs.edit()
    }

    companion object {
        const val KEY_CITY = "key_city"

        private val APP_SHARED_PREFS =
            AppPreferences::class.java.simpleName

    }

    val city: String?
        get() = _sharedPrefs.getString(KEY_CITY,"")

    fun saveCity(type: String) {
        _prefsEditor.putString(KEY_CITY, type)
        _prefsEditor.commit()
    }

}