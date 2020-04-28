package com.yanyushkin.memes.di

import com.yanyushkin.memes.App
import com.yanyushkin.memes.ui.activities.auth.AuthVM
import com.yanyushkin.memes.ui.activities.main.fragments.memes.MemesVM
import com.yanyushkin.memes.ui.activities.newMeme.NewMemeVM
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, MemesDatabaseModule::class])
interface AppComponent {

    fun injectsAuthVM(authVM: AuthVM)

    fun injectsMemesVM(memesVM: MemesVM)

    fun injectsNewMemeVM(newMemeVM: NewMemeVM)
}