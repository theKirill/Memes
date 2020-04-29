package com.yanyushkin.memes.ui.activities.main.fragments.user

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.App
import com.yanyushkin.memes.database.DbRepository
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.domain.UserInfo
import com.yanyushkin.memes.network.AuthRepository
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.storage.UserStorage
import java.net.UnknownHostException
import javax.inject.Inject

class UserVM : ViewModel() {

    @Inject
    lateinit var dbRepository: DbRepository
    @Inject
    lateinit var authRepository: AuthRepository
    val stateLogout = MutableLiveData<ScreenState>()
    val stateMemes = MutableLiveData<ScreenState>()
    val memes = MutableLiveData<MutableList<Meme>>()
    val userInfo = MutableLiveData<UserInfo>()
    var rotated = false

    init {
        App.component.injectsUserVM(this)
    }

    fun getUserInfo(context: Context) {
        userInfo.value = UserStorage(context).getUserInfo()
    }

    @SuppressLint("CheckResult")
    fun getMemes() {
        if (!rotated) {
            val memesFromDb = mutableListOf<Meme>()

            dbRepository.getMemes().subscribe({
                it.forEach {
                    memesFromDb.add(it.convertToDomain())
                }
                memes.value = memesFromDb
                stateMemes.value = ScreenState.SUCCESS
            }, {
                stateMemes.value = ScreenState.ERROR_OTHER
            })
        }
    }

    fun logout(context: Context) =
        authRepository.logout().subscribe({
            UserStorage(context).deleteUserInfo()
            stateLogout.value = ScreenState.SUCCESS
        }, {
            if (it is UnknownHostException)
                stateLogout.value = ScreenState.ERROR_NO_INTERNET
            else
                stateLogout.value = ScreenState.SUCCESS
        })
}