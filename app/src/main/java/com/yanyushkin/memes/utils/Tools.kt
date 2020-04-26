package com.yanyushkin.memes.utils

import java.util.*

fun getDifferenceBetweenDatesInDays(memeDate: Long): Long {
    val currentDate = Date()

    val datesDifference = currentDate.time - memeDate

    return datesDifference / (24 * 60 * 60 * 1000)
}