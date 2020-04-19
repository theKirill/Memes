package com.yanyushkin.memes.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yanyushkin.memes.R
import com.yanyushkin.memes.domain.Meme
import kotlinx.android.synthetic.main.card_meme.view.*

class MemesAdapter(private var memes: MutableList<Meme>) :
    RecyclerView.Adapter<MemesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate
                (R.layout.card_meme, parent, false)
        )

    override fun getItemCount(): Int = memes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    fun setMemes(memes: MutableList<Meme>) {
        this.memes = memes
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        init {
            v.setOnClickListener { }
        }

        fun bind(pos: Int) {
            val meme = memes[pos]
            setImage(meme.photoUrl)
            setHeader(meme.title)
            setLike(meme.isFavourite)

            setClickListenerOnLikeButton(pos)
        }

        private fun setImage(imageUrl: String) =
            Glide.with(itemView.meme_cv).load(imageUrl).into(itemView.meme_image_iv)

        private fun setHeader(title: String) {
            itemView.title_tv.text = title
        }

        private fun setLike(like: Boolean) {
            val imageLike = if (like)
                R.drawable.ic_like_full
            else
                R.drawable.ic_like_empty

            itemView.like_btn.setImageResource(imageLike)
        }

        private fun setClickListenerOnLikeButton(i: Int) {
            itemView.like_btn.setOnClickListener {
                val imageLike = if (memes[i].isFavourite)
                    R.drawable.ic_like_empty
                else
                    R.drawable.ic_like_full

                memes[i].isFavourite = !memes[i].isFavourite
                itemView.like_btn.setImageResource(imageLike)
            }
        }
    }
}