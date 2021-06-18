package com.app.androidjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity

class DetailDataViewModel(private val myRepository: MyRepository):ViewModel() {

    fun getDetailMovies(idmovie:String): LiveData<MovieEntity> = myRepository.getDetailMovie(idmovie)

    fun getDetailTvs(idtv:String): LiveData<TvEntity> = myRepository.getDetailTV(idtv)

}