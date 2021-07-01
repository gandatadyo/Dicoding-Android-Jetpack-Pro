package com.app.androidjetpack.ui.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class TvAdapter(private val detailCallback: (itemId: String, mode: String) -> Unit): PagedListAdapter<ItemTvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemTvEntity>() {
            override fun areItemsTheSame(oldItem: ItemTvEntity, newItem: ItemTvEntity): Boolean {
                return oldItem.itemId == newItem.itemId
            }
            override fun areContentsTheSame(oldItem: ItemTvEntity, newItem: ItemTvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class TvViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val urlimg = "https://image.tmdb.org/t/p/original"
        fun bind(item: ItemTvEntity) {
            with(binding) {
                tvItemTitle.text = item.title
                tvItemDate.text = itemView.resources.getString(R.string.info_date, item.dateItem)
                Glide.with(itemView.context).load(urlimg+item.imagePath).into(imgPoster)
                itemView.setOnClickListener {
                    detailCallback.invoke(item.itemId, "tv")
                }
            }
        }
    }
}