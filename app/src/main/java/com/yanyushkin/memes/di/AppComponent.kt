package com.yanyushkin.memes.di

import com.yanyushkin.memes.ui.activities.auth.AuthVM
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun injectsAuthVM(authVM: AuthVM)
}