package com.app.androidjetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.bumptech.glide.Glide
import java.util.ArrayList

class TvAdapter: PagedListAdapter<ItemEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemEntity>() {
            override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
                return oldItem.itemId == newItem.itemId
            }
            override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var listMovies = ArrayList<ItemEntity>()

    fun setTvs(items: List<ItemEntity>?) {
        if (items == null) return
        this.listMovies.clear()
        this.listMovies.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsAcademyBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val item = listMovies[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listMovies.size

    class TvViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val urlimg = "https://image.tmdb.org/t/p/original"
        fun bind(item: ItemEntity) {
            with(binding) {
                tvItemTitle.text = item.title
                tvItemDate.text = itemView.resources.getString(R.string.info_date, item.dateItem)
                Glide.with(itemView.context).load(urlimg+item.imagePath).into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailItemActivity::class.java)
                    intent.putExtra(DetailItemActivity.EXTRA_ITEM, item.itemId)
                    intent.putExtra(DetailItemActivity.EXTRA_MODE, "tv")
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}