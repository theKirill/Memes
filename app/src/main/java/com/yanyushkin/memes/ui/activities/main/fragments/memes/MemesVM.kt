package com.yanyushkin.memes.ui.activities.main.fragments.memes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yanyushkin.memes.App
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.network.MemesRepository
import com.yanyushkin.memes.states.ScreenState
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

    fun getMemes() {
        val memesFromServer = mutableListOf<Meme>()

        repository.getMemes().subscribe({
            it.forEach {
                memesFromServer.add(it.transform())
            }
            memes.value = memesFromServer
            state.value = ScreenState.SUCCESS
        },
            {
                state.value = ScreenState.ERROR_OTHER
            })
    }
}