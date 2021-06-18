package com.app.androidjetpack.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.R
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.databinding.ActivityDetailItemBinding
import com.app.androidjetpack.databinding.ContentDetailBinding
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide

class DetailItemActivity : AppCompatActivity() {
    private lateinit var detailContentBinding : ContentDetailBinding
    private val urlimg = "https://image.tmdb.org/t/p/original"

    companion object {
        const val EXTRA_ITEM = "extra_detail_item"
        const val EXTRA_MODE = "extra_detail_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailItemBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this,factory)[DetailDataViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getString(EXTRA_ITEM)
            val mode = extras.getString(EXTRA_MODE)
            if (itemId != null && mode != null) {

                if(mode=="movie"){
                    supportActionBar?.title = "Detail Movie"
                    EspressoIdlingResource.increment()
                    viewModel.getDetailMovies(itemId).observe(this, { movie ->
                        populateItemMovie(movie)
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                            //Memberitahukan bahwa tugas sudah selesai dijalankan
                            EspressoIdlingResource.decrement()
                        }
                    })
                }else if(mode=="tv"){
                    supportActionBar?.title = "Detail TV"
                    EspressoIdlingResource.increment()
                    viewModel.getDetailTvs(itemId).observe(this, { tv ->
                        populateItemTv(tv)
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                            //Memberitahukan bahwa tugas sudah selesai dijalankan
                            EspressoIdlingResource.decrement()
                        }
                    })
                }

            }
        }
    }

    private fun populateItemMovie(item: MovieEntity) {
        detailContentBinding.textTitle.text = item.titleMovie
        detailContentBinding.textDescription.text = item.descMovie
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateMovie)
        Glide.with(this).load(urlimg+item.imgMovie).into(detailContentBinding.imagePoster)
    }

    private fun populateItemTv(item: TvEntity) {
        detailContentBinding.textTitle.text = item.titleTv
        detailContentBinding.textDescription.text = item.descTv
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateTv)
        Glide.with(this).load(urlimg+item.imgTv).into(detailContentBinding.imagePoster)
    }
}