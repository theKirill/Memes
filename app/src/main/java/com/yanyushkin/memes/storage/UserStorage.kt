package com.yanyushkin.memes.storage

import android.content.Context
import android.content.SharedPreferences
import com.yanyushkin.memes.domain.UserInfo

class UserStorage(context: Context) {

    private var prefs: SharedPreferences
    private val allKeys = listOf(
        ACCESS_TOKEN_KEY,
        ID_KEY, USERNAME_KEY, FIRST_NAME_KEY, LAST_NAME_KEY, DESCRIPTION_KEY
    )

    init {
        prefs = context.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun saveUserInfo(accessToken: String, userInfo: UserInfo) {
        prefs.edit().putString(allKeys[0], accessToken).apply()
        prefs.edit().putInt(allKeys[1], userInfo.id).apply()
        prefs.edit().putString(allKeys[2], userInfo.username).apply()
        prefs.edit().putString(allKeys[3], userInfo.firstName).apply()
        prefs.edit().putString(allKeys[4], userInfo.lastName).apply()
        prefs.edit().putString(allKeys[5], userInfo.description).apply()
    }

    fun getAccessToken(): String = prefs.getString(ACCESS_TOKEN_KEY, "") ?: ""

    fun getUserInfo(): UserInfo {
        val id = prefs.getInt(allKeys[1], 0)
        val username = prefs.getString(allKeys[2], "") ?: ""
        val firstName = prefs.getString(allKeys[3], "") ?: ""
        val lastName = prefs.getString(allKeys[4], "") ?: ""
        val description = prefs.getString(allKeys[5], "") ?: ""

        return UserInfo(id, username, firstName, lastName, description)
    }

    fun deleteUserInfo() =
        with(prefs.edit()) {
            allKeys.forEach {
                remove(it).apply()
            }
        }

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