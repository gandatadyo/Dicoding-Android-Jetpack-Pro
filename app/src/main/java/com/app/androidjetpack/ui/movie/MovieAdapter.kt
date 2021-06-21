package com.app.androidjetpack.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.R
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.bumptech.glide.Glide
import java.util.ArrayList

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>()  {
    private var listMovies = ArrayList<ItemEntity>()

    fun setMovies(items: List<ItemEntity>?) {
        if (items == null) return
        this.listMovies.clear()
        this.listMovies.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsAcademyBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = listMovies[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listMovies.size

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val urlimg = "https://image.tmdb.org/t/p/original"
        fun bind(item: ItemEntity) {
            with(binding) {
                tvItemTitle.text = item.title
                tvItemDate.text = itemView.resources.getString(R.string.info_date, item.dateItem)
                Glide.with(itemView.context).load(urlimg+item.imagePath).into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailItemActivity::class.java)
                    intent.putExtra(DetailItemActivity.EXTRA_ITEM, item.itemId)
                    intent.putExtra(DetailItemActivity.EXTRA_MODE, "movie")
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}