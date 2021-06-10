package com.app.androidjetpack.ui.movie

import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.utils.DataDummy

class MovieViewModel: ViewModel() {
    fun getMovies(): List<ItemEntity> = DataDummy.generateDummyMovie()
}