package com.yanyushkin.memes.network.responses

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.yanyushkin.memes.domain.UserInfo

data class UserInfoResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("userDescription")
    val description: String?
) : BaseResponse<UserInfo> {
    override fun transform(): UserInfo {
        return UserInfo(
            id ?: 0,
            username ?: "",
            firstName ?: "",
            lastName ?: "",
            description ?: ""
        )
    }
}