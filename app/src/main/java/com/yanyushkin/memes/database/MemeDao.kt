package com.yanyushkin.memes.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface MemeDao {

    @Query("SELECT * FROM meme")
    fun getAllMemes(): Flowable<List<Meme>>

    @Query("SELECT * FROM meme WHERE title LIKE :title")
    fun filterMemes(title: String): List<Meme>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeme(meme: Meme): Completable

    @Update
    fun updateMeme(meme: Meme)
}