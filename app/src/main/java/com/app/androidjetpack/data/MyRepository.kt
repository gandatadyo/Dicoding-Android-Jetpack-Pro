package com.app.androidjetpack.data

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.androidjetpack.data.entity.MovieEntity
import com.app.androidjetpack.data.entity.TvEntity
import com.app.androidjetpack.data.remote.RemoteDataSource

class MyRepository private constructor(private val remoteDataSource: RemoteDataSource):MyDataSource {

    companion object {
        @Volatile
        private var instance: MyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MyRepository =
            instance ?: synchronized(this) {
                MyRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllMovie():LiveData<List<MovieEntity>> {
        val movieResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getAllMovies(object :RemoteDataSource.LoadAllMovieCallback{
            override fun onAllCoursesReceived(moviesResponse: List<MovieEntity>) {
                val movieList = ArrayList<MovieEntity>()
                for(response in moviesResponse){
                    movieList.add(
                        MovieEntity(
                            response.id,
                            response.titleMovie,
                            response.dateMovie,
                            response.descMovie,
                            response.imgMovie,
                        )
                    )
                }
                movieResult.postValue(movieList)
            }

        })
        return movieResult
    }

    override fun getDetailMovie():MovieEntity{
        val movie = MovieEntity(
            "test",
            "test",
            "test",
            "test",
            "test"
        )
        return movie
    }

    override fun getAllTv():List<TvEntity>{
        val tv = ArrayList<TvEntity>()
        return tv
    }

    override fun getDetailTV():TvEntity{
        val tv = TvEntity(
            "test",
            "test",
            "test",
            "test",
            "test"
        )
        return tv
    }
}