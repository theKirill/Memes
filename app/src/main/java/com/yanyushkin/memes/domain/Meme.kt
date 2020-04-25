package com.yanyushkin.memes.domain

import java.io.Serializable

data class Meme (
    val id: Long,
    val title: String,
    val description: String,
    var isFavourite: Boolean,
    val createdDate: Long,
    val photoUrl: String
) : Serializable