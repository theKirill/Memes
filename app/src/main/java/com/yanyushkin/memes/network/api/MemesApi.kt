package com.yanyushkin.memes.network.api

import com.yanyushkin.memes.network.responses.MemesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface MemesApi {

    @GET("memes")
    fun getMemes(): Observable<List<MemesResponse>>
}