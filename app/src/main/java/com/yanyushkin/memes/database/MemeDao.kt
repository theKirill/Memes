package com.yanyushkin.memes.database

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface MemeDao {

    @Query("SELECT * FROM meme")
    fun getAllMemes(): List<Meme>

    @Query("SELECT * FROM meme WHERE title LIKE :title")
    fun filterMemes(title: String): List<Meme>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeme(meme: Meme)

    @Update
    fun updateMeme(meme: Meme)
}