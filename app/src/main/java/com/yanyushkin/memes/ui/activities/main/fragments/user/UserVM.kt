package com.yanyushkin.memes.ui.activities.main.fragments.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.domain.UserInfo
import com.yanyushkin.memes.network.AuthRepository
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.storage.UserStorage
import java.net.UnknownHostException
import javax.inject.Inject

class UserVM : ViewModel() {

    @Inject
    lateinit var repository: AuthRepository
    val stateLogout = MutableLiveData<ScreenState>()
    val userInfo = MutableLiveData<UserInfo>()

    init {
    }

    fun getUserInfo(context: Context) {
        userInfo.value = UserStorage(context).getUserInfo()
    }

    fun logout(context: Context) =
        repository.logout().subscribe({
            UserStorage(context).deleteUserInfo()
            stateLogout.value = ScreenState.SUCCESS
        }, {
            if (it is UnknownHostException)
                stateLogout.value = ScreenState.ERROR_NO_INTERNET
            else
                stateLogout.value = ScreenState.ERROR_OTHER
        })
}