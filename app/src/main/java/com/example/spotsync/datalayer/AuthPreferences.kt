package com.example.spotsync.datalayer

import android.content.Context

class AuthPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
    private val authKey = "is_authenticated"

    fun isAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(authKey, false)
    }

    fun setAuthenticated(isAuthenticated: Boolean) {
        sharedPreferences.edit().putBoolean(authKey, isAuthenticated).apply()
    }

    fun clearAuthState() {
        sharedPreferences.edit().remove(authKey).apply()
    }
}
