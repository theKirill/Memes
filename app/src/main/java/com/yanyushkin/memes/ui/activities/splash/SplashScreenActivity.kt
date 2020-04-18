package com.yanyushkin.memes.ui.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yanyushkin.memes.R
import com.yanyushkin.memes.ui.activities.auth.AuthorizationActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        openNextActivity()
    }

    private fun openNextActivity() {
        Handler().postDelayed({
            val openLoginActivityIntent = Intent(this, AuthorizationActivity::class.java)
            startActivity(openLoginActivityIntent)
        }, 300)
    }
}
