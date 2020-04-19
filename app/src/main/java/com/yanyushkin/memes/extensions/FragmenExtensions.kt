package com.yanyushkin.memes.extensions

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.yanyushkin.memes.R
import com.yanyushkin.memes.SNACKBAR_DURATION

@SuppressLint("WrongConstant")
fun Fragment.showSnackBar(view: View, context: FragmentActivity?, messageId: Int) {
    context?.let {
        val snackBar = Snackbar.make(view, messageId, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(it, R.color.colorError))
        snackBar.apply {
            duration = SNACKBAR_DURATION
            show()
        }
    }
}