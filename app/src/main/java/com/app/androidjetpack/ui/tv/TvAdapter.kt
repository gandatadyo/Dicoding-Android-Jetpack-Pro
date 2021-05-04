package com.app.androidjetpack.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidjetpack.data.ItemEntity
import com.app.androidjetpack.databinding.ItemMovieBinding
import com.app.androidjetpack.ui.detail.DetailMovieActivity
import java.util.ArrayList

class TvAdapter: RecyclerView.Adapter<TvAdapter.CourseViewHolder>()  {
    private var listMovies = ArrayList<ItemEntity>()

    fun setTvs(courses: List<ItemEntity>?) {
        if (courses == null) return
        this.listMovies.clear()
        this.listMovies.addAll(courses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsAcademyBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = listMovies[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listMovies.size

    class CourseViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: ItemEntity) {
            with(binding) {
                tvItemTitle.text = tv.title
//                tvItemDate.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_COURSE, tv.itemId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}