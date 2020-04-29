package com.yanyushkin.memes.network.api

import com.yanyushkin.memes.network.requests.AuthCredentialsRequest
import com.yanyushkin.memes.network.responses.AuthResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    fun auth(@Body authCredentialsRequest: AuthCredentialsRequest): Observable<AuthResponse>

    @POST("auth/logout")
    fun logout(): Observable<Unit>
}