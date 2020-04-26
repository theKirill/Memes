package com.yanyushkin.memes.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yanyushkin.memes.R
import com.yanyushkin.memes.domain.Meme
import com.yanyushkin.memes.utils.OnClickListener
import kotlinx.android.synthetic.main.card_meme.view.*

class MemesAdapter(
    private var memes: MutableList<Meme>,
    private val onMemeClickListener: OnClickListener,
    private val onShareClickListener: OnClickListener
) :
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
            v.setOnClickListener {
                onMemeClickListener.onClickView(adapterPosition)
            }
        }

        fun bind(pos: Int) {
            val meme = memes[pos]
            setImage(meme.photoUrl)
            setHeader(meme.title)
            setLike(meme.isFavourite)

            setClickListenerOnLikeButton(pos)
            setClickListenerOnShareButton(pos)
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

        // TODO: добавить сохранение состояния кнопки лайка
        private fun setClickListenerOnLikeButton(i: Int) =
            itemView.like_btn.setOnClickListener {
                memes[i].isFavourite = !memes[i].isFavourite
                setLike(memes[i].isFavourite)
            }

        private fun setClickListenerOnShareButton(i: Int) =
            itemView.share_btn.setOnClickListener {
                onShareClickListener.onClickView(i)
            }
    }
}