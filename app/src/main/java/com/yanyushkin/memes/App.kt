package com.yanyushkin.memes

import android.app.Application
import com.yanyushkin.memes.di.AppComponent
import com.yanyushkin.memes.di.DaggerAppComponent
import com.yanyushkin.memes.di.NetworkModule

class App : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }
}