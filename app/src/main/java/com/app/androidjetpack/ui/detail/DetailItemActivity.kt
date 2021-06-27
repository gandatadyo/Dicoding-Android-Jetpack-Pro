package com.app.androidjetpack.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.databinding.ActivityDetailItemBinding
import com.app.androidjetpack.databinding.ContentDetailBinding
import com.app.androidjetpack.utils.EspressoIdlingResource
import com.app.androidjetpack.viewmodel.ViewModelFactory
import com.app.androidjetpack.vo.Status
import com.bumptech.glide.Glide

class DetailItemActivity : AppCompatActivity() {
    private lateinit var detailContentBinding : ContentDetailBinding
    private val urlimg = "https://image.tmdb.org/t/p/original"

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

    private fun populateItemMovie(item: ItemEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }

    private fun populateItemTv(item: ItemEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        Glide.with(this).load(urlimg+item.imagePath).into(detailContentBinding.imagePoster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.courseModule.observe(this, { courseWithModule ->
            if (courseWithModule != null) {
                when (courseWithModule.status) {
                    Status.LOADING -> activityDetailCourseBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (courseWithModule.data != null) {
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                        val state = courseWithModule.data.mCourse.bookmarked
                        setBookmarkState(state)
                    }
                    Status.ERROR -> {
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            viewModel.setBookmark()
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