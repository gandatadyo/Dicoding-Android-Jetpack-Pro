package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.source.local.ItemEntity

interface MyDataSource {

    fun getAllMovie(): LiveData<List<ItemEntity>>

    fun getDetailMovie(idmovie:String): LiveData<ItemEntity>

    fun getAllTv(): LiveData<List<ItemEntity>>

    fun getDetailTV(idtv:String): LiveData<ItemEntity>

}