package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity

interface MyDataSource {

    fun getAllMovie(): LiveData<List<MovieEntity>>

    fun getDetailMovie(): MovieEntity

    fun getAllTv(): LiveData<List<TvEntity>>

    fun getDetailTV(): TvEntity

}