package com.app.androidjetpack.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.vo.Resource

class MovieViewModel(private val myRepository: MyRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<ItemMovieEntity>>> = myRepository.getAllMovie()

}