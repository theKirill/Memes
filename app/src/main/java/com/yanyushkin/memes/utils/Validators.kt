package com.yanyushkin.memes.utils

import com.yanyushkin.memes.PASSWORD_LENGTH

fun validField(text: String) = text.isNotEmpty()
fun validPassLen(text: String) = text.length >= PASSWORD_LENGTH