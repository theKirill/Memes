package com.yanyushkin.memes.ui.activities.main.fragments.memes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.App
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.network.MemesRepository
import com.yanyushkin.memes.states.AuthState
import javax.inject.Inject

class MemesVM : ViewModel() {

    @Inject
    lateinit var repository: MemesRepository
    val memes = MutableLiveData<MutableList<Meme>>()
    val state = MutableLiveData<AuthState>()

    init {
        App.component.injectsMemesVM(this)
    }

    fun getMemes() {
        val memesFromServer = mutableListOf<Meme>()

        repository.getMemes().subscribe({
            it.forEach {
                memesFromServer.add(it.transform())
            }
            memes.value = memesFromServer
            state.value = AuthState.SUCCESS
        },
            {
                state.value = AuthState.ERROR_OTHER
            })
    }
}