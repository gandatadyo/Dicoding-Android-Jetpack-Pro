package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.source.local.ItemEntity
import com.app.androidjetpack.vo.Resource

interface MyDataSource {

    fun getAllMovie(): LiveData<Resource<List<ItemEntity>>>

    fun getAllTv(): LiveData<Resource<List<ItemEntity>>>

    fun getDetailMovie(idmovie:String): LiveData<ItemEntity>

    fun getDetailTV(idtv:String): LiveData<ItemEntity>

}