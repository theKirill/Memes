package com.yanyushkin.memes.extensions

import com.yanyushkin.memes.network.responses.AuthResponse
import com.yanyushkin.memes.network.responses.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.getDataInBackground() = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
