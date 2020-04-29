package com.yanyushkin.memes.ui.activities.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.*
import com.yanyushkin.memes.network.AuthRepository
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.storage.UserStorage
import com.yanyushkin.memes.utils.validField
import com.yanyushkin.memes.utils.validPassLen
import java.net.UnknownHostException
import javax.inject.Inject

class AuthVM : ViewModel() {

    @Inject
    lateinit var repository: AuthRepository
    val state = MutableLiveData<ScreenState>()
    val loginValid = MutableLiveData<Boolean>()
    val passwordValid = MutableLiveData<Boolean>()
    val passwordVisible = MutableLiveData<Boolean>()
    private lateinit var userStorage: UserStorage

    init {
        App.component.injectsAuthVM(this)
        passwordVisible.value = false
    }

    fun auth(login: String, password: String, context: Context) {
        loginValid.value = validField(login)
        passwordValid.value = validField(password) && validPassLen(password)

        if (loginValid.value!! && passwordValid.value!!) {
            repository.auth(login, password).subscribe({
                UserStorage(context).saveUserInfo(it.accessToken, it.userInfo.transform())
                state.value = ScreenState.SUCCESS
            }, {
                if (it is UnknownHostException)
                    state.value = ScreenState.ERROR_NO_INTERNET
                else
                    state.value = ScreenState.ERROR_NOT_VALID_DATA
            })
        }
    }
}