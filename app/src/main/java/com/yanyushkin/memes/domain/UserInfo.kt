package com.yanyushkin.memes.domain

import java.io.Serializable

data class UserInfo (
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val description: String
) : Serializable