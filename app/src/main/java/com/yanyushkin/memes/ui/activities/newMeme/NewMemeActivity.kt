package com.yanyushkin.memes.ui.activities.newMeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yanyushkin.memes.R
import com.yanyushkin.memes.extensions.*
import com.yanyushkin.memes.utils.validDescriptionTitleLen
import com.yanyushkin.memes.utils.validField
import com.yanyushkin.memes.utils.validMemeTitleLen
import kotlinx.android.synthetic.main.activity_new_meme.*
import kotlinx.android.synthetic.main.toolbar_new_meme.*

class NewMemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MainTheme)
        setContentView(R.layout.activity_new_meme)

        setOnClickListeners()
        initTextChangeWatchers()
    }

    private fun setOnClickListeners() {
        close_creating_meme_btn.setOnClickListener { onBackPressed() }

        create_meme_btn.setOnClickListener { }

        new_meme_load_img_btn.setOnClickListener {
            hideKeyBoard()
            new_meme_image_layout.show()
        }

        new_meme_remove_img_btn.setOnClickListener {
            hideKeyBoard()
            new_meme_image_iv.setImageDrawable(null)
            new_meme_image_layout.gone()
        }
    }

    private fun hideKeyBoard() {
        hideKeyboard(this, new_meme_load_img_btn)
        new_meme_layout.requestFocus()
    }

    private fun initTextChangeWatchers() {
        new_meme_title_et.afterTextChanged {
            if (!validMemeTitleLen(it))
                new_meme_error_title_tv.show()
            else
                new_meme_error_title_tv.gone()

            checkTitleAndDescriptionFields()
        }

        new_meme_description_et.afterTextChanged {
            if (!validDescriptionTitleLen(it))
                new_meme_error_descr_tv.show()
            else
                new_meme_error_descr_tv.gone()

            checkTitleAndDescriptionFields()
        }
    }

    private fun checkTitleAndDescriptionFields() {
        create_meme_btn.isEnabled =
            validField(new_meme_title_et.text.toString()) && validField(new_meme_description_et.text.toString())
    }
}
