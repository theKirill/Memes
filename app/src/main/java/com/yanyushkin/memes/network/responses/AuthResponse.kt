package com.yanyushkin.memes.network.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("userInfo")
    val userInfo: UserInfoResponse
)