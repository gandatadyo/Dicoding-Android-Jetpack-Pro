package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.remote.response.ItemResponseEntity

interface MyDataSource {

    fun getAllMovie(): LiveData<List<ItemResponseEntity>>

    fun getDetailMovie(idmovie:String): LiveData<ItemResponseEntity>

    fun getAllTv(): LiveData<List<ItemResponseEntity>>

    fun getDetailTV(idtv:String): LiveData<ItemResponseEntity>

}