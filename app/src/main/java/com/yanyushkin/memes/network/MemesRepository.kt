package com.yanyushkin.memes.network

import com.yanyushkin.memes.extensions.getDataInBackground
import com.yanyushkin.memes.network.api.MemesApi
import javax.inject.Inject

class MemesRepository @Inject constructor(private val memesApi: MemesApi) {

    fun getMemes(accessToken: String) = memesApi.getMemes(accessToken).getDataInBackground()
}