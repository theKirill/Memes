package com.yanyushkin.memes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meme (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    var isFavourite: Boolean,
    val createdDate: Long,
    val photoUrl: String
)