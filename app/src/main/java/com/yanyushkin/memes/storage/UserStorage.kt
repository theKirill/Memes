package com.yanyushkin.memes.storage

import android.content.Context
import android.content.SharedPreferences
import com.yanyushkin.memes.domain.UserInfo

class UserStorage (context: Context) {

    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun saveUserInfo(accessToken: String, userInfo: UserInfo) {
        prefs.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
        prefs.edit().putInt(ID_KEY, userInfo.id).apply()
        prefs.edit().putString(USERNAME_KEY, userInfo.username).apply()
        prefs.edit().putString(FIRST_NAME_KEY, userInfo.firstName).apply()
        prefs.edit().putString(LAST_NAME_KEY, userInfo.lastName).apply()
        prefs.edit().putString(DESCRIPTION_KEY, userInfo.description).apply()
    }

    fun getField(key: String): Any = prefs.getString(key, "") ?: ""

    companion object {
        private const val APP_PREFERENCES_KEY = "settings"
        private const val ACCESS_TOKEN_KEY = "accessToken"
        private const val ID_KEY = "id"
        private const val USERNAME_KEY = "username"
        private const val FIRST_NAME_KEY = "firstName"
        private const val LAST_NAME_KEY = "lastName"
        private const val DESCRIPTION_KEY = "description"
    }
}