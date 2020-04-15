package com.yanyushkin.memes.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yanyushkin.memes.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() { }
}
