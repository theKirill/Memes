package com.yanyushkin.memes.ui.activities.main.fragments.user

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanyushkin.memes.MEME_KEY
import com.yanyushkin.memes.R
import com.yanyushkin.memes.adapters.MemesAdapter
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.extensions.gone
import com.yanyushkin.memes.extensions.show
import com.yanyushkin.memes.extensions.showSnackBar
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.ui.activities.auth.AuthorizationActivity
import com.yanyushkin.memes.ui.activities.detailing.DetailingMemeActivity
import com.yanyushkin.memes.utils.BaseViewModelFactory
import com.yanyushkin.memes.utils.OnClickListener
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private object Holder {
        val INSTANCE = UserFragment()
    }

    companion object {
        val instance: UserFragment by lazy { Holder.INSTANCE }
    }

    private lateinit var userVM: UserVM
    private lateinit var adapter: MemesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        userVM = ViewModelProvider(this, BaseViewModelFactory { UserVM() }).get(UserVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initToolbar()

        userVM.getUserInfo(activity as Context)

        initObservers()
    }

    override fun onStart() {
        super.onStart()

        userVM.getMemes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        userVM.rotated = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_menu -> {
            }
            R.id.logout_menu -> {
                showAlertDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        val thisActivity = activity as AppCompatActivity

        user_toolbar.overflowIcon?.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            ),
            PorterDuff.Mode.SRC_ATOP
        )

        thisActivity.setSupportActionBar(user_toolbar)
        thisActivity.supportActionBar!!.title = null

        setHasOptionsMenu(true)
    }

    private fun initObservers() {
        userVM.userInfo.observe(this, Observer {
            username_tv.text = it.username
            user_description_tv.text = it.description
        })

        userVM.stateMemes.observe(this, Observer {
            when (it) {
                ScreenState.SUCCESS -> {
                    showMemes()
                }
                ScreenState.ERROR_OTHER -> {
                    swipe_user_memes_layout.gone()
                    error_user_memes_layout.show()
                }
            }
            userVM.rotated = false
            swipe_user_memes_layout.isRefreshing = false
            progress_user_memes_layout.gone()
        })

        userVM.stateLogout.observe(this, Observer {
            when (it) {
                ScreenState.SUCCESS -> openAuthorizationActivity()
                ScreenState.ERROR_NO_INTERNET -> showSnackBar(
                    user_data_layout,
                    activity,
                    R.string.auth_no_internet_sb
                )
                ScreenState.ERROR_OTHER -> showSnackBar(
                    username_tv,
                    activity,
                    R.string.error_sb
                )
            }
        })
    }

    private fun showMemes() {
        userVM.memes.value?.let {
            if (!swipe_user_memes_layout.isRefreshing) {
                initAdapter(it)
                user_memes_rv.adapter = adapter
            } else {
                adapter.setMemes(it)
            }
        }
        swipe_user_memes_layout.show()
    }

    private fun initAdapter(memes: MutableList<Meme>) {
        adapter = MemesAdapter(memes, object : OnClickListener {
            override fun onClickView(position: Int) =
                openDetailingMemeActivity(memes[position])
        }, object : OnClickListener {
            override fun onClickView(position: Int) = shareMeme(memes[position])
        })
    }

    private fun openDetailingMemeActivity(meme: Meme) =
        Intent(activity, DetailingMemeActivity::class.java).putExtra(MEME_KEY, meme)
            .run {
                startActivity(this)
            }

    private fun shareMeme(meme: Meme) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, meme.title)
            putExtra(Intent.EXTRA_TEXT, meme.photoUrl)
            putExtra(Intent.EXTRA_TEXT, meme.description)
            type = "text/plain"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        val shareIntent =
            Intent.createChooser(
                sendIntent,
                getString(R.string.share_meme_text)
            )
        startActivity(shareIntent)
    }

    private fun showAlertDialog() {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle(R.string.dialog_are_you_sure_title)
            .setPositiveButton(R.string.dialog_logout_btn) { _, _ -> userVM.logout(activity as Context) }
            .setNegativeButton(R.string.dialog_cancel_btn) { _, _ -> }
        alert.create().show()
    }

    private fun openAuthorizationActivity() =
        Intent(activity, AuthorizationActivity::class.java).run {
            startActivity(this)
        }
}