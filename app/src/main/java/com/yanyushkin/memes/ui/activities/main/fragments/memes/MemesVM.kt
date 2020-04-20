package com.yanyushkin.memes.ui.activities.main.fragments.memes

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.App
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.network.MemesRepository
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.storage.UserStorage
import javax.inject.Inject

class MemesVM : ViewModel() {

    @Inject
    lateinit var repository: MemesRepository
    val memes = MutableLiveData<MutableList<Meme>>()
    val state = MutableLiveData<ScreenState>()
    var rotated = false

    init {
        App.component.injectsMemesVM(this)
    }

    fun getMemes(accessToken: String) {
        val memesFromServer = mutableListOf<Meme>()

        if (accessToken.isNotEmpty()) {
            repository.getMemes(accessToken).subscribe({
                it.forEach {
                    memesFromServer.add(it.transform())
                }
                memes.value = memesFromServer
                state.value = ScreenState.SUCCESS
            },
                {
                    state.value = ScreenState.ERROR_OTHER
                })
        } else {
            state.value = ScreenState.ERROR_OTHER
        }
    }
}