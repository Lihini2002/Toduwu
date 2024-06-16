package com.example.spotsync.datalayer

import android.content.Context
import android.content.SharedPreferences


// SharedPreferences helper class
class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply() // or use commit() if immediate write is needed
        }
    }

    fun readData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Add other methods as needed for different data types (Int, Boolean, etc.)
}
