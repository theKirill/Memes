package com.yanyushkin.memes.ui.activities.main.fragments.memes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yanyushkin.memes.MEME_KEY
import com.yanyushkin.memes.R
import com.yanyushkin.memes.adapters.MemesAdapter
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.extensions.gone
import com.yanyushkin.memes.extensions.show
import com.yanyushkin.memes.extensions.showSnackBar
import com.yanyushkin.memes.states.ScreenState
import com.yanyushkin.memes.ui.activities.detailing.DetailingMemeActivity
import com.yanyushkin.memes.utils.BaseViewModelFactory
import com.yanyushkin.memes.utils.OnClickListener
import kotlinx.android.synthetic.main.card_meme.*
import kotlinx.android.synthetic.main.fragment_memes.*

class MemesFragment : Fragment() {

    private object Holder {
        val INSTANCE = MemesFragment()
    }

    companion object {
        val instance: MemesFragment by lazy { Holder.INSTANCE }
    }

    private lateinit var memesViewModel: MemesVM
    private lateinit var adapter: MemesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        memesViewModel =
            ViewModelProvider(this, BaseViewModelFactory { MemesVM() }).get(MemesVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_memes, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        memesViewModel.getMemes(activity as Context)

        initRecyclerView()
        initSwipeRefreshListener()
        initObservers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        memesViewModel.rotated = true
    }

    private fun initRecyclerView() {
        memes_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun initSwipeRefreshListener() {
        swipe_memes_layout.setColorSchemeResources(R.color.colorBackground)
        swipe_memes_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent)
        swipe_memes_layout.setOnRefreshListener {
            memesViewModel.getMemes(activity as Context)
        }
    }

    private fun initObservers() {
        memesViewModel.state.observe(this, Observer<ScreenState> {
            when (it) {
                ScreenState.SUCCESS -> {
                    showMemes()
                }
                ScreenState.ERROR_NO_INTERNET -> {
                    showMemes()
                    showSnackBar(memes_main_layout, activity, R.string.auth_no_internet_sb)
                }
                ScreenState.ERROR_OTHER -> {
                    swipe_memes_layout.gone()
                    error_memes_layout.show()
                }
            }
            memesViewModel.rotated = false
            swipe_memes_layout.isRefreshing = false
            progress_memes_layout.gone()
        })
    }

    private fun showMemes() {
        memesViewModel.memes.value?.let {
            if (!swipe_memes_layout.isRefreshing) {
                initAdapter(it)
                memes_rv.adapter = adapter
            } else {
                adapter.setMemes(it)
            }
        }
        swipe_memes_layout.show()
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
}