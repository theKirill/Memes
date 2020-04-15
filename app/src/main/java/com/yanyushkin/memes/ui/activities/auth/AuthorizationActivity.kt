package com.yanyushkin.memes.ui.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanyushkin.memes.utils.BaseViewModelFactory
import com.yanyushkin.memes.PASSWORD_LENGTH
import com.yanyushkin.memes.R
import com.yanyushkin.memes.extensions.hide
import com.yanyushkin.memes.extensions.hideKeyboard
import com.yanyushkin.memes.extensions.show
import com.yanyushkin.memes.extensions.showSnackBar
import com.yanyushkin.memes.states.AuthState
import com.yanyushkin.memes.ui.activities.main.MainActivity
import com.yanyushkin.memes.utils.validPassLen
import kotlinx.android.synthetic.main.activity_authorization.*

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthVM
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        initViews()

        authViewModel =
            ViewModelProvider(this,
                BaseViewModelFactory { AuthVM() }).get(AuthVM::class.java)

        initStateObserver()
    }

    override fun onBackPressed() {}

    private fun initViews() {
        initPasswordTextChangeWatcher()
        initLoginButtonClickListener()
        initIconShowPassClickListener()
    }

    private fun initPasswordTextChangeWatcher() {
        password_tf.setSimpleTextChangeWatcher { theNewText, isError ->
            if (!validPassLen(theNewText))
                password_tf.helperText = "Пароль должен содержать $PASSWORD_LENGTH символов"
            else
                password_tf.helperText = ""
        }
    }

    private fun initLoginButtonClickListener() {
        login_btn.setOnClickListener {
            login_pb.show()
            login_btn.text = ""
            hideKeyBoard()
            authViewModel.auth(login_et.text.toString(), password_et.text.toString())
        }
    }

    private fun initIconShowPassClickListener() {
        password_tf.endIconImageButton.setOnClickListener { changePasswordInputType() }
    }

    private fun hideKeyBoard() {
        hideKeyboard(this, login_btn)
        login_main_layout.requestFocus()
    }

    private fun changePasswordInputType() {
        val icon = if (!passwordVisible)
            R.drawable.ic_close_eye
        else
            R.drawable.ic_eye

        val inputType = if (!passwordVisible)
            InputType.TYPE_CLASS_TEXT
        else
            InputType.TYPE_TEXT_VARIATION_PASSWORD

        passwordVisible = !passwordVisible
        password_et.inputType = inputType
        password_tf.setEndIcon(icon)
    }

    private fun initStateObserver() {
        authViewModel.loginValid.observe(this, Observer<Boolean> {
            when (it) {
                false -> {
                    login_tf.setError(getString(R.string.auth_error_text), false)
                    login_pb.hide()
                    login_btn.text = getText(R.string.auth_login_btn)
                }
            }
        })
        authViewModel.passwordValid.observe(this, Observer<Boolean> {
            when (it) {
                false -> {
                    if (password_et.text.isEmpty()) {
                        password_tf.setError(getString(R.string.auth_error_text), false)
                    }
                    login_pb.hide()
                    login_btn.text = getText(R.string.auth_login_btn)
                }
            }
        })
        authViewModel.state.observe(this, Observer<AuthState> {
            when (it) {
                AuthState.SUCCESS -> {
                    authViewModel.saveUserInfo(this)
                    openMainActivity()
                }
                AuthState.ERROR_NOT_VALID_DATA -> {
                    showSnackBar(
                        login_main_layout,
                        this,
                        R.string.auth_not_valid_data_sb
                    )
                    login_pb.hide()
                    login_btn.text = getText(R.string.auth_login_btn)
                }
                AuthState.ERROR_NO_INTERNET -> {
                    showSnackBar(
                        login_main_layout,
                        this,
                        R.string.auth_no_internet_sb
                    )
                    login_pb.hide()
                    login_btn.text = getText(R.string.auth_login_btn)
                }
            }
        })
    }

    private fun openMainActivity() {
        val openMainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(openMainActivityIntent)
    }
}
