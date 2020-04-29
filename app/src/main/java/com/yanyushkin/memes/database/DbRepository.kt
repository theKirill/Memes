package com.yanyushkin.memes.database

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DbRepository @Inject constructor(private val memeDao: MemeDao) {

    fun getMemes() = memeDao.getAllMemes().observeOn(AndroidSchedulers.mainThread())

    fun addMeme(meme: Meme) =
        Observable.just {
            memeDao.insertMeme(meme)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun changeLike(meme: Meme) {

    }
}