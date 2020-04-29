package com.yanyushkin.memes.ui.activities.newMeme

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanyushkin.memes.R
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.extensions.*
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.utils.BaseViewModelFactory
import com.yanyushkin.memes.utils.validDescriptionTitleLen
import com.yanyushkin.memes.utils.validField
import com.yanyushkin.memes.utils.validMemeTitleLen
import kotlinx.android.synthetic.main.activity_new_meme.*
import kotlinx.android.synthetic.main.toolbar_new_meme.*
import java.util.*

class NewMemeActivity : AppCompatActivity() {

    private lateinit var newMemeViewModel: NewMemeVM
    private val REQUEST_CODE_CAMERA: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MainTheme)
        setContentView(R.layout.activity_new_meme)

        newMemeViewModel =
            ViewModelProvider(this,
                BaseViewModelFactory { NewMemeVM() }).get(NewMemeVM::class.java)

        initObservers()
        setOnClickListeners()
        initTextChangeWatchers()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCameraActivity()
            else
                Toast.makeText(this, R.string.no_camera_permission, Toast.LENGTH_SHORT).show()
        }
    }

    // TODO доделать сохранение фото в галерею
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val photo = it.extras?.get("data") as Bitmap
                    new_meme_image_iv.setImageBitmap(photo)
                }
            } else if (resultCode != Activity.RESULT_CANCELED) {
                showSnackBar(
                    new_meme_layout,
                    this,
                    R.string.error_sb
                )
            }
        }
    }

    private fun initObservers() {
        newMemeViewModel.state.observe(this, Observer {
            when (it) {
                ScreenState.SUCCESS -> {
                    Toast.makeText(this, R.string.new_meme_success, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    finish()
                }
                ScreenState.ERROR_OTHER -> {
                    showSnackBar(
                        new_meme_layout,
                        this,
                        R.string.error_sb
                    )
                }
            }
        })
    }

    private fun setOnClickListeners() {
        close_creating_meme_btn.setOnClickListener { onBackPressed() }

        create_meme_btn.setOnClickListener {
            newMemeViewModel.addMeme(createMemeDomain())
        }

        new_meme_load_img_btn.setOnClickListener {
            hideKeyBoard()
            uploadPhotoBtnClickListener()
            new_meme_image_layout.show()
        }

        new_meme_remove_img_btn.setOnClickListener {
            hideKeyBoard()
            new_meme_image_iv.setImageDrawable(null)
            new_meme_image_layout.gone()
        }
    }

    private fun uploadPhotoBtnClickListener() {
        val permissionStatus =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permissionStatus == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                Toast.makeText(this, R.string.request_camera_permission, Toast.LENGTH_LONG).show()

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        } else {
            openCameraActivity()
        }
    }

    private fun openCameraActivity() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).run {
            startActivityForResult(this, REQUEST_CODE_CAMERA)
        }
    }

    private fun createMemeDomain(): Meme {
        val title = new_meme_title_et.text.toString()
        val description = new_meme_description_et.text.toString()
        val curDate = Date().time
        val photoUrl = new_meme_image_iv.tag.toString()

        return Meme(
            id = 0,
            title = title,
            description = description,
            isFavourite = false,
            createdDate = curDate,
            photoUrl = photoUrl
        )
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
