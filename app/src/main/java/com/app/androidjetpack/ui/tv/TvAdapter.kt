package com.app.androidjetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.R
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.app.androidjetpack.ui.detail.DetailItemActivity
import com.bumptech.glide.Glide
import java.util.ArrayList

class TvAdapter: RecyclerView.Adapter<TvAdapter.TvViewHolder>()  {
    private var listMovies = ArrayList<TvEntity>()

    fun setTvs(items: List<TvEntity>?) {
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
        fun bind(item: TvEntity) {
            with(binding) {
                tvItemTitle.text = item.titleTv
                tvItemDate.text = itemView.resources.getString(R.string.info_date, item.dateTv)
                Glide.with(itemView.context).load(item.imgTv).into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailItemActivity::class.java)
                    intent.putExtra(DetailItemActivity.EXTRA_ITEM, item.id)
                    intent.putExtra(DetailItemActivity.EXTRA_MODE, "tv")
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}