package com.yanyushkin.memes.network

import com.yanyushkin.memes.extensions.run
import com.yanyushkin.memes.network.api.AuthApi
import com.yanyushkin.memes.network.requests.AuthCredentialsRequest
import javax.inject.Inject

class Repository @Inject constructor(private val authApi: AuthApi) {

    fun auth(login: String, password: String) =
        authApi.auth(AuthCredentialsRequest(login, password)).run()
}