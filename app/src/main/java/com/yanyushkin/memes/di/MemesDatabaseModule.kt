package com.yanyushkin.memes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yanyushkin.memes.DATABASE_NAME
import com.yanyushkin.memes.database.AppDatabase
import com.yanyushkin.memes.database.MemeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MemesDatabaseModule(val context: Context) {

    @Singleton
    @Provides
    fun provideDb(): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMemeDao(db: AppDatabase): MemeDao = db.memeDao()
}