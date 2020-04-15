package com.yanyushkin.memes.network.responses

interface BaseResponse<T> {

    fun transform(): T
}