package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.vo.Resource

interface MyDataSource {

    // movie
    fun getAllMovie(): LiveData<Resource<PagedList<ItemMovieEntity>>>

    fun getDetailMovie(idmovie:String): LiveData<Resource<ItemMovieEntity>>

    fun getBookmarkedMovies(filter: String): LiveData<PagedList<ItemMovieEntity>>

    fun setMovieBookmark(movie: ItemMovieEntity, state: Boolean)

    // tv
    fun getAllTv(): LiveData<Resource<PagedList<ItemTvEntity>>>

    fun getDetailTV(idtv:String): LiveData<Resource<ItemTvEntity>>

    fun getBookmarkedTvs(filter: String): LiveData<PagedList<ItemTvEntity>>

    fun setTvBookmark(movie: ItemTvEntity, state: Boolean)

}