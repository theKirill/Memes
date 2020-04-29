package com.yanyushkin.memes.network

import com.yanyushkin.memes.extensions.sendRequestInBackground
import com.yanyushkin.memes.network.api.AuthApi
import com.yanyushkin.memes.network.requests.AuthCredentialsRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    fun auth(login: String, password: String) =
        authApi.auth(AuthCredentialsRequest(login, password)).sendRequestInBackground()

    fun logout() = authApi.logout().sendRequestInBackground()
}