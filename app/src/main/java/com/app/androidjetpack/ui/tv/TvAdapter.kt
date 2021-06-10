package com.app.androidjetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import java.util.ArrayList

class TvAdapter: RecyclerView.Adapter<TvAdapter.TvViewHolder>()  {
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
        fun bind(item: ItemEntity) {
            with(binding) {
                tvItemTitle.text = item.title
                tvItemDate.text = itemView.resources.getString(R.string.info_date, item.dateItem)
                imgPoster.setImageDrawable(itemView.context.resources.getDrawable(item.imagePath))
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