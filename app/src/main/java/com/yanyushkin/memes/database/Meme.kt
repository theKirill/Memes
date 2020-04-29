package com.yanyushkin.memes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yanyushkin.memes.domain.Meme

@Entity
data class Meme(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    var isFavourite: Boolean,
    val createdDate: Long,
    val photoUrl: String
) {
    fun convertToDomain() = Meme(
        id, title, description, isFavourite, createdDate, photoUrl
    )
}