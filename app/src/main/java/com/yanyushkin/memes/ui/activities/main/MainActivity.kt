package com.yanyushkin.memes.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.yanyushkin.memes.FRAGMENT_ID_KEY
import com.yanyushkin.memes.R
import com.yanyushkin.memes.ui.activities.main.fragments.memes.MemesFragment
import com.yanyushkin.memes.ui.activities.main.fragments.new_meme.NewMemeFragment
import com.yanyushkin.memes.ui.activities.main.fragments.user.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val memesFragment = MemesFragment.instance
    private val newMemeFragment = NewMemeFragment()
    private val userFragment = UserFragment()
    private lateinit var currentFragment: Fragment
    private lateinit var currentFragmentId: String
    private val fragments =
        mapOf("memes" to memesFragment, "new_meme" to newMemeFragment, "user" to userFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MainTheme)
        setContentView(R.layout.activity_main)

        addFragments(savedInstanceState)
        setOnNavItemSelectedListener()
    }

    override fun onBackPressed() {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(FRAGMENT_ID_KEY, currentFragmentId)
    }

    private fun addFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            currentFragmentId = "memes"
        } else {
            currentFragmentId = savedInstanceState.getString(FRAGMENT_ID_KEY, "memes")
        }

        currentFragment = fragments[currentFragmentId]!!

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragments.values.forEach {
            fragmentTransaction.add(R.id.fragment_container, it).hide(it)
        }
        fragmentTransaction.show(currentFragment)
        fragmentTransaction.commit()
    }

    private fun setOnNavItemSelectedListener() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.memes_item -> {
                    changeFragment(memesFragment, currentFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.new_meme_item -> {
                    changeFragment(newMemeFragment, currentFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.user_item -> {
                    changeFragment(userFragment, currentFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun changeFragment(newFragment: Fragment, oldFragment: Fragment) {
        if (newFragment != oldFragment) {
            supportFragmentManager.beginTransaction().show(newFragment).hide(oldFragment).commit()
            currentFragment = newFragment
            val indexOfCurrentFragment = fragments.values.indexOf(currentFragment)
            currentFragmentId = fragments.keys.elementAt(indexOfCurrentFragment)
        }
    }
}
