package com.app.androidjetpack.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.androidjetpack.R
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.databinding.ActivityDetailItemBinding
import com.app.androidjetpack.databinding.ContentDetailBinding

class DetailItemActivity : AppCompatActivity() {
    private lateinit var detailContentBinding : ContentDetailBinding

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


        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getString(EXTRA_ITEM)
            val mode = extras.getString(EXTRA_MODE)
            if (itemId != null && mode != null) {
                viewModel.setSelectedData(itemId)
                if(mode=="movie"){
                    supportActionBar?.title = "Detail Movie"
                }else if(mode=="tv"){
                    supportActionBar?.title = "Detail TV"
                }
                populateItem(viewModel.getData(mode) as ItemEntity)
            }
        }
    }


    private fun populateItem(item: ItemEntity) {
        detailContentBinding.textTitle.text = item.title
        detailContentBinding.textDescription.text = item.description
        detailContentBinding.textDate.text = resources.getString(R.string.info_date, item.dateItem)
        detailContentBinding.imagePoster.setImageDrawable(resources.getDrawable(item.imagePath))
    }
}