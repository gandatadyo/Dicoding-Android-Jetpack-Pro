package com.app.androidjetpack.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.app.androidjetpack.data.source.local.entity.ItemMovieEntity
import com.app.androidjetpack.data.source.local.entity.ItemTvEntity
import com.app.androidjetpack.data.source.local.room.MovieTvDao
import com.app.androidjetpack.utils.SortUtils

class LocalDataSource private constructor(private val movietvDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDao)
    }

    // movie

    fun getAllMovies(): DataSource.Factory<Int, ItemMovieEntity> = movietvDao.getMovies()

    fun getBookmarkedMovies(sort: String): DataSource.Factory<Int, ItemMovieEntity> {
        val query = SortUtils.getBookmarkedMovieQuery(sort)
        return movietvDao.getBookmarkedMovie(query)
    }

    fun getMovieDetail(itemID: String): LiveData<ItemMovieEntity> = movietvDao.getMovieDetail(itemID)

    fun insertMovies(movies: List<ItemMovieEntity>) = movietvDao.insertMovies(movies)

    fun setMovieBookmark(movie: ItemMovieEntity, newState: Boolean) {
        movie.bookmarked = newState
        movietvDao.updateMovie(movie)
    }

    // tv

    fun getAllTvs(): DataSource.Factory<Int, ItemTvEntity> = movietvDao.getTvs()

    fun getBookmarkedTvs(sort: String): DataSource.Factory<Int, ItemTvEntity> {
        val query = SortUtils.getBookmarkedTvQuery(sort)
        return movietvDao.getBookmarkedTv(query)
    }

    fun getTvDetail(itemID: String): LiveData<ItemTvEntity> = movietvDao.getTvDetail(itemID)

    fun insertTvs(tvs: List<ItemTvEntity>) = movietvDao.insertTvs(tvs)

    fun setTvBookmark(tv: ItemTvEntity, newState: Boolean) {
        tv.bookmarked = newState
        movietvDao.updateTv(tv)
    }
}
