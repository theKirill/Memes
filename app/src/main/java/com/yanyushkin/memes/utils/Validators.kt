package com.yanyushkin.memes.utils

import com.yanyushkin.memes.MEME_DESCRIPTION_LENGTH
import com.yanyushkin.memes.MEME_TITLE_LENGTH
import com.yanyushkin.memes.PASSWORD_LENGTH

fun validField(text: String) = text.isNotEmpty()
fun validPassLen(text: String) = text.length >= PASSWORD_LENGTH

fun validMemeTitleLen(text: String) = text.length < MEME_TITLE_LENGTH
fun validDescriptionTitleLen(text: String) = text.length < MEME_DESCRIPTION_LENGTH