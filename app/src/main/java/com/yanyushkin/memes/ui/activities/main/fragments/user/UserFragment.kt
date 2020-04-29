package com.yanyushkin.memes.ui.activities.main.fragments.user

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanyushkin.memes.R
import com.yanyushkin.memes.extensions.showSnackBar
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.ui.activities.auth.AuthorizationActivity
import com.yanyushkin.memes.utils.BaseViewModelFactory
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private object Holder {
        val INSTANCE = UserFragment()
    }

    companion object {
        val instance: UserFragment by lazy { Holder.INSTANCE }
    }

    private lateinit var userVM: UserVM

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

        userVM.getUserInfo(activity as Context)

        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
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

    private fun initObservers() {
        userVM.userInfo.observe(this, Observer {
            username_tv.text = it.username
            user_description_tv.text = it.description
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

    private fun showAlertDialog() {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle(R.string.dialog_are_you_sure_title)
            .setPositiveButton(R.string.dialog_logout_btn) { _, _ -> userVM.logout(activity as Context) }
            .setNegativeButton(R.string.dialog_cancel_btn) { _, _ -> }
        alert.create().show()
    }

    //TODO: запретить возвращаться на главный экран
    private fun openAuthorizationActivity() {
        val openAuthorizationActivityIntent = Intent(activity, AuthorizationActivity::class.java)
        startActivity(openAuthorizationActivityIntent)
        //fini
    }
}