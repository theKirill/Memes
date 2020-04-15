package com.yanyushkin.memes.ui.activities.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.*
import com.yanyushkin.memes.domain.UserInfo
import com.yanyushkin.memes.network.Repository
import com.yanyushkin.memes.states.AuthState
import com.yanyushkin.memes.utils.SettingsOfApp
import com.yanyushkin.memes.utils.validField
import com.yanyushkin.memes.utils.validPassLen
import java.net.UnknownHostException
import javax.inject.Inject

class AuthVM : ViewModel() {

    private lateinit var token: String
    private lateinit var userInfo: UserInfo
    @Inject
    lateinit var repository: Repository
    val state = MutableLiveData<AuthState>()
    val loginValid = MutableLiveData<Boolean>()
    val passwordValid = MutableLiveData<Boolean>()

    init {
        App.component.injectsAuthVM(this)
    }

    fun auth(login: String, password: String) {
        loginValid.value = validField(login)
        passwordValid.value = validField(password)

        if (loginValid.value!! && passwordValid.value!! && validPassLen(password)) {
            repository.auth(login, password).subscribe({
                token = it.accessToken
                userInfo = it.userInfo.transform()
                state.value = AuthState.SUCCESS
            }, {
                if (it is UnknownHostException)
                    state.value = AuthState.ERROR_NO_INTERNET
                else
                    state.value = AuthState.ERROR_NOT_VALID_DATA
            })
        }
    }

    fun saveUserInfo(context: Context) {
        SettingsOfApp.initPreferences(context)
        SettingsOfApp.saveField(ACCESS_TOKEN_KEY, token)
        SettingsOfApp.saveField(ID_KEY, userInfo.id)
        SettingsOfApp.saveField(USERNAME_KEY, userInfo.username)
        SettingsOfApp.saveField(FIRST_NAME_KEY, userInfo.firstName)
        SettingsOfApp.saveField(LAST_NAME_KEY, userInfo.lastName)
        SettingsOfApp.saveField(DESCRIPTION_KEY, userInfo.description)
    }
}