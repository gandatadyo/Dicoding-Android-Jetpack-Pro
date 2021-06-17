package com.app.androidjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.utils.ModulRestapi

class MovieDataViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getMovies(): LiveData<List<MovieEntity>> = myRepository.getAllMovie()

}