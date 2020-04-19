package com.yanyushkin.memes.network.responses

import com.google.gson.annotations.SerializedName
import com.yanyushkin.memes.domain.Meme

data class MemesResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isFavorite")
    val isFavourite: Boolean?,
    @SerializedName("createdDate")
    val createdDate: Long?,
    @SerializedName("photoUrl")
    val photoUrl: String?
) : BaseResponse<Meme> {

    override fun transform(): Meme =
        Meme(
            id ?: 0,
            title ?: "",
            description ?: "",
            isFavourite ?: false,
            createdDate ?: 0,
            photoUrl ?: ""
        )
}