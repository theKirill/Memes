package com.yanyushkin.memes.di

import com.yanyushkin.memes.ui.activities.auth.AuthVM
import com.yanyushkin.memes.ui.activities.main.fragments.memes.MemesVM
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun injectsAuthVM(authVM: AuthVM)

    fun injectsMemesVM(memesVM: MemesVM)
}