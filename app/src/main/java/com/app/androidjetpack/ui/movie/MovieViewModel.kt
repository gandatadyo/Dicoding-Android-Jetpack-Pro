package com.app.androidjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.vo.Resource

class MovieViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<List<ItemEntity>>> = myRepository.getAllMovie()

}