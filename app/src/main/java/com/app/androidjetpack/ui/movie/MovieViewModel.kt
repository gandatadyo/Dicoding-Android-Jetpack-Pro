package com.app.androidjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity

class MovieViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getMovies(): LiveData<List<ItemEntity>> = myRepository.getAllMovie()

}