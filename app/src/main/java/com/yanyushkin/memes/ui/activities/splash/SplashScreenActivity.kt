package com.yanyushkin.memes.ui.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yanyushkin.memes.R
import com.yanyushkin.memes.storage.UserStorage
import com.yanyushkin.memes.ui.activities.auth.AuthorizationActivity
import com.yanyushkin.memes.ui.activities.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        openNextActivity()
    }

    private fun openNextActivity() {
        val userStorage = UserStorage(this)
        Handler().postDelayed({
            val intent = if (userStorage.getAccessToken().isNotEmpty())
                getOpenMainActivityIntent()
            else
                getOpenLoginActivityIntent()

            startActivity(intent)
            finish()
        }, 300)
    }

    private fun getOpenLoginActivityIntent(): Intent =
        Intent(this, AuthorizationActivity::class.java)

    private fun getOpenMainActivityIntent(): Intent = Intent(this, MainActivity::class.java)
}
