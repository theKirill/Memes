package com.yanyushkin.memes.network.api

import com.yanyushkin.memes.network.responses.MemesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface MemesApi {

    @GET("memes")
    fun getMemes(@Header("Authorization") accessToken: String): Observable<List<MemesResponse>>
}