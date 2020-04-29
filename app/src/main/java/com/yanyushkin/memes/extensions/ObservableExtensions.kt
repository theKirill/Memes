package com.yanyushkin.memes.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.sendRequestInBackground() = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
