package com.app.androidjetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidjetpack.data.MyRepository
import com.app.androidjetpack.data.remote.response.ItemResponseEntity
import com.app.androidjetpack.data.source.local.ItemEntity

class DetailViewModel(private val myRepository: MyRepository):ViewModel() {

    fun getDetailMovies(idmovie:String): LiveData<ItemEntity> = myRepository.getDetailMovie(idmovie)

    fun getDetailTvs(idtv:String): LiveData<ItemEntity> = myRepository.getDetailTV(idtv)

}