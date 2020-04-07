package com.yanyushkin.memes.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.Navigation
import com.yanyushkin.memes.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        Handler().postDelayed({
            Navigation.findNavController(this, R.id.splash_screen_nav_host_fragment)
                .navigate(R.id.action_splashScreenFragment_to_authorizationActivity)
        }, 300)
    }
}
