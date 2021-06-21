package com.app.androidjetpack.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.R
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity
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
        detailContentBinding.loadingView.visibility = View.GONE

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this,factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getString(EXTRA_ITEM)
            val mode = extras.getString(EXTRA_MODE)
            if (itemId != null && mode != null) {

                if(mode=="movie"){
                    supportActionBar?.title = "Detail Movie"
                    EspressoIdlingResource.increment()
                    detailContentBinding.loadingView.visibility = View.VISIBLE
                    viewModel.getDetailMovies(itemId).observe(this, { movie ->
                        detailContentBinding.loadingView.visibility = View.GONE
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                            //Memberitahukan bahwa tugas sudah selesai dijalankan
                            EspressoIdlingResource.decrement()
                        }
                        populateItemMovie(movie)
                    })
                }else if(mode=="tv"){
                    supportActionBar?.title = "Detail TV"
                    EspressoIdlingResource.increment()
                    detailContentBinding.loadingView.visibility = View.VISIBLE
                    viewModel.getDetailTvs(itemId).observe(this, { tv ->
                        detailContentBinding.loadingView.visibility = View.GONE
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                            //Memberitahukan bahwa tugas sudah selesai dijalankan
                            EspressoIdlingResource.decrement()
                        }
                        populateItemTv(tv)
                    })
                }

            }
        }
    }

    private fun populateItemMovie(item: ItemResponseEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }

    private fun populateItemTv(item: ItemResponseEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }
}