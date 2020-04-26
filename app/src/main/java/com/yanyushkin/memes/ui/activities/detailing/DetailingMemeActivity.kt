package com.yanyushkin.memes.ui.activities.detailing

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.bumptech.glide.Glide
import com.yanyushkin.memes.MEME_KEY
import com.yanyushkin.memes.R
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.utils.getDifferenceBetweenDatesInDays
import kotlinx.android.synthetic.main.activity_detailing_meme.*
import kotlinx.android.synthetic.main.toolbar_detailing_meme.*
import java.util.*

class DetailingMemeActivity : AppCompatActivity() {

    private lateinit var meme: Meme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MainTheme)
        setContentView(R.layout.activity_detailing_meme)

        fillActivity()

        close_meme_btn.setOnClickListener { onBackPressed() }
    }

    private fun fillActivity() {
        val arguments = intent.extras
        meme = arguments?.get(MEME_KEY) as Meme

        setTitle()
        setImage()
        setDate()
        setLike()
        setDescription()
    }

    private fun setTitle() {
        meme_title_tv.text = meme.title
    }

    private fun setImage() = Glide.with(meme_main_layout).load(meme.photoUrl).into(meme_image_iv)

    @SuppressLint("SetTextI18n")
    private fun setDate() {
        meme_date_tv.text =
            getDifferenceBetweenDatesInDays(meme.createdDate).toString() + " " + getString(R.string.meme_days_ago_text)
    }

    private fun setLike() {
        val imageLike = if (meme.isFavourite)
            R.drawable.ic_like_full
        else
            R.drawable.ic_like_empty

        meme_like_btn.setImageResource(imageLike)
    }

    private fun setDescription() {
        val description = meme.description

        meme_description_tv.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
    }
}
