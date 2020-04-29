package com.yanyushkin.memes.ui.activities.newMeme

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.*
import com.yanyushkin.memes.database.DbRepository
import com.yanyushkin.memes.database.MemeDao
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.network.AuthRepository
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.storage.UserStorage
import com.yanyushkin.memes.utils.validField
import com.yanyushkin.memes.utils.validPassLen
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class NewMemeVM() : ViewModel() {

    @Inject
    lateinit var dbRepository: DbRepository
    val state = MutableLiveData<ScreenState>()

    init {
        App.component.injectsNewMemeVM(this)
    }

    @SuppressLint("CheckResult")
    fun addMeme(meme: Meme) {
        dbRepository.addMeme(
            meme.convertToDbEntity()
        ).subscribe({
            state.value = ScreenState.SUCCESS
        }, {
            state.value = ScreenState.ERROR_OTHER
        })
    }
}