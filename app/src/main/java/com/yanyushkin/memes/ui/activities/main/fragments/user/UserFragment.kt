package com.yanyushkin.memes.ui.activities.main.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yanyushkin.memes.R
import com.yanyushkin.memes.adapters.MemesAdapter
import com.yanyushkin.memes.extensions.gone
import com.yanyushkin.memes.extensions.show
import com.yanyushkin.memes.states.AuthState
import com.yanyushkin.memes.utils.BaseViewModelFactory
import kotlinx.android.synthetic.main.fragment_memes.*

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user, container, false)
}