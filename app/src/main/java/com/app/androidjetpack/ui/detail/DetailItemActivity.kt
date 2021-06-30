package com.app.androidjetpack.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.databinding.ActivityDetailItemBinding
import com.app.androidjetpack.databinding.ContentDetailBinding
import com.app.androidjetpack.viewmodel.ViewModelFactory
import com.app.androidjetpack.vo.Status
import com.bumptech.glide.Glide

class DetailItemActivity : AppCompatActivity() {
    private lateinit var detailContentBinding : ContentDetailBinding
    private val urlimg = "https://image.tmdb.org/t/p/original"
    private var modeData:String? = ""

    private lateinit var viewModel :DetailViewModel

    companion object {
        const val EXTRA_ITEM = "extra_detail_item"
        const val EXTRA_MODE = "extra_detail_mode"
    }

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailItemBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailBinding.detailContent
        detailContentBinding.loadingView.visibility = View.GONE

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this,factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getString(EXTRA_ITEM)
            modeData = extras.getString(EXTRA_MODE)
            if (itemId != null && modeData != null) {
                viewModel.setSelectedItem(itemId)


                if(modeData=="movie"){
                    supportActionBar?.title = "Detail Movie"
                    detailContentBinding.loadingView.visibility = View.VISIBLE
                    viewModel.itemMovieModule.observe(this, { movie ->
                        if (movie != null) {
                            when (movie.status) {
                                Status.LOADING -> detailContentBinding.loadingView.visibility = View.VISIBLE
                                Status.SUCCESS -> if (movie.data != null) {
                                    detailContentBinding.loadingView.visibility = View.GONE
                                    movie.data?.let { populateItemMovie(it) }
                                }
                                Status.ERROR -> {
                                    detailContentBinding.loadingView.visibility = View.GONE
                                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }else if(modeData=="tv"){
                    supportActionBar?.title = "Detail TV"
                    detailContentBinding.loadingView.visibility = View.VISIBLE
                    viewModel.itemTvModule.observe(this, { movie ->
                        if (movie != null) {
                            when (movie.status) {
                                Status.LOADING -> detailContentBinding.loadingView.visibility = View.VISIBLE
                                Status.SUCCESS -> if (movie.data != null) {
                                    detailContentBinding.loadingView.visibility = View.GONE
                                    movie.data?.let { populateItemTv(it) }
                                }
                                Status.ERROR -> {
                                    detailContentBinding.loadingView.visibility = View.GONE
                                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }


            }
        }
    }

    private fun populateItemMovie(item: ItemMovieEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }

    private fun populateItemTv(item: ItemTvEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        if(modeData=="movie"){
            viewModel.itemMovieModule.observe(this, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> detailContentBinding.loadingView.visibility = View.VISIBLE
                        Status.SUCCESS -> if (movie.data != null) {
                            detailContentBinding.loadingView.visibility = View.GONE
                            val state = movie.data.bookmarked
                            setBookmarkState(state)
                        }
                        Status.ERROR -> {
                            detailContentBinding.loadingView.visibility = View.GONE
                            Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }else if(modeData=="tv"){
            viewModel.itemTvModule.observe(this, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> detailContentBinding.loadingView.visibility = View.VISIBLE
                        Status.SUCCESS -> if (tv.data != null) {
                            detailContentBinding.loadingView.visibility = View.GONE
                            val state = tv.data.bookmarked
                            setBookmarkState(state)
                        }
                        Status.ERROR -> {
                            detailContentBinding.loadingView.visibility = View.GONE
                            Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            if(modeData=="movie"){
                viewModel.setBookmarkMovie()
            }else if(modeData=="tv"){
                viewModel.setBookmarkTv()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmarked_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_white)
        }
    }
}