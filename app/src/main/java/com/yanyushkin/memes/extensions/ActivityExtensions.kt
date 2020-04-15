package com.yanyushkin.memes.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.yanyushkin.memes.R
import com.yanyushkin.memes.SNACKBAR_DURATION

fun AppCompatActivity.hideKeyboard(context: AppCompatActivity, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

@SuppressLint("WrongConstant")
fun AppCompatActivity.showSnackBar(view: View, context: AppCompatActivity, messageId: Int) {
    val snackBar = Snackbar.make(view, messageId, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
    snackBar.apply {
        duration = SNACKBAR_DURATION
        show()
    }
}