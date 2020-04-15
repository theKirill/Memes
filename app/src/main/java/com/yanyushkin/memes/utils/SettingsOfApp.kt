package com.yanyushkin.memes.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.yanyushkin.memes.APP_PREFERENCES_KEY

object SettingsOfApp {

    private lateinit var prefs: SharedPreferences

    fun initPreferences(context: Context) {
        prefs = context.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun saveField(key: String, field: Any) {
        when (field) {
            is String -> prefs.edit().putString(key, field).apply()
            is Int -> prefs.edit().putInt(key, field).apply()
        }
    }

    fun getField(key: String): Any = prefs.getString(key, "") ?: ""

    fun removeField(key: String): Unit = prefs.edit().remove(key).apply()

    private fun hasField(key: String) = prefs.contains(key)

}